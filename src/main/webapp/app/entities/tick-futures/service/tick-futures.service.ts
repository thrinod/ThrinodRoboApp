import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITickFutures, NewTickFutures } from '../tick-futures.model';

export type PartialUpdateTickFutures = Partial<ITickFutures> & Pick<ITickFutures, 'id'>;

export type EntityResponseType = HttpResponse<ITickFutures>;
export type EntityArrayResponseType = HttpResponse<ITickFutures[]>;

@Injectable({ providedIn: 'root' })
export class TickFuturesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tick-futures');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(tickFutures: NewTickFutures): Observable<EntityResponseType> {
    return this.http.post<ITickFutures>(this.resourceUrl, tickFutures, { observe: 'response' });
  }

  update(tickFutures: ITickFutures): Observable<EntityResponseType> {
    return this.http.put<ITickFutures>(`${this.resourceUrl}/${this.getTickFuturesIdentifier(tickFutures)}`, tickFutures, {
      observe: 'response',
    });
  }

  partialUpdate(tickFutures: PartialUpdateTickFutures): Observable<EntityResponseType> {
    return this.http.patch<ITickFutures>(`${this.resourceUrl}/${this.getTickFuturesIdentifier(tickFutures)}`, tickFutures, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITickFutures>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITickFutures[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTickFuturesIdentifier(tickFutures: Pick<ITickFutures, 'id'>): number {
    return tickFutures.id;
  }

  compareTickFutures(o1: Pick<ITickFutures, 'id'> | null, o2: Pick<ITickFutures, 'id'> | null): boolean {
    return o1 && o2 ? this.getTickFuturesIdentifier(o1) === this.getTickFuturesIdentifier(o2) : o1 === o2;
  }

  addTickFuturesToCollectionIfMissing<Type extends Pick<ITickFutures, 'id'>>(
    tickFuturesCollection: Type[],
    ...tickFuturesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tickFutures: Type[] = tickFuturesToCheck.filter(isPresent);
    if (tickFutures.length > 0) {
      const tickFuturesCollectionIdentifiers = tickFuturesCollection.map(
        tickFuturesItem => this.getTickFuturesIdentifier(tickFuturesItem)!,
      );
      const tickFuturesToAdd = tickFutures.filter(tickFuturesItem => {
        const tickFuturesIdentifier = this.getTickFuturesIdentifier(tickFuturesItem);
        if (tickFuturesCollectionIdentifiers.includes(tickFuturesIdentifier)) {
          return false;
        }
        tickFuturesCollectionIdentifiers.push(tickFuturesIdentifier);
        return true;
      });
      return [...tickFuturesToAdd, ...tickFuturesCollection];
    }
    return tickFuturesCollection;
  }
}
