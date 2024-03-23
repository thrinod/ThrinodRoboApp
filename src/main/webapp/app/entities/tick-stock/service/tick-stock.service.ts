import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITickStock, NewTickStock } from '../tick-stock.model';

export type PartialUpdateTickStock = Partial<ITickStock> & Pick<ITickStock, 'id'>;

export type EntityResponseType = HttpResponse<ITickStock>;
export type EntityArrayResponseType = HttpResponse<ITickStock[]>;

@Injectable({ providedIn: 'root' })
export class TickStockService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tick-stocks');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(tickStock: NewTickStock): Observable<EntityResponseType> {
    return this.http.post<ITickStock>(this.resourceUrl, tickStock, { observe: 'response' });
  }

  update(tickStock: ITickStock): Observable<EntityResponseType> {
    return this.http.put<ITickStock>(`${this.resourceUrl}/${this.getTickStockIdentifier(tickStock)}`, tickStock, { observe: 'response' });
  }

  partialUpdate(tickStock: PartialUpdateTickStock): Observable<EntityResponseType> {
    return this.http.patch<ITickStock>(`${this.resourceUrl}/${this.getTickStockIdentifier(tickStock)}`, tickStock, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITickStock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITickStock[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTickStockIdentifier(tickStock: Pick<ITickStock, 'id'>): number {
    return tickStock.id;
  }

  compareTickStock(o1: Pick<ITickStock, 'id'> | null, o2: Pick<ITickStock, 'id'> | null): boolean {
    return o1 && o2 ? this.getTickStockIdentifier(o1) === this.getTickStockIdentifier(o2) : o1 === o2;
  }

  addTickStockToCollectionIfMissing<Type extends Pick<ITickStock, 'id'>>(
    tickStockCollection: Type[],
    ...tickStocksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tickStocks: Type[] = tickStocksToCheck.filter(isPresent);
    if (tickStocks.length > 0) {
      const tickStockCollectionIdentifiers = tickStockCollection.map(tickStockItem => this.getTickStockIdentifier(tickStockItem)!);
      const tickStocksToAdd = tickStocks.filter(tickStockItem => {
        const tickStockIdentifier = this.getTickStockIdentifier(tickStockItem);
        if (tickStockCollectionIdentifiers.includes(tickStockIdentifier)) {
          return false;
        }
        tickStockCollectionIdentifiers.push(tickStockIdentifier);
        return true;
      });
      return [...tickStocksToAdd, ...tickStockCollection];
    }
    return tickStockCollection;
  }
}
