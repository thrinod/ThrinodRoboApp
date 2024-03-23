import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITickOption, NewTickOption } from '../tick-option.model';

export type PartialUpdateTickOption = Partial<ITickOption> & Pick<ITickOption, 'id'>;

export type EntityResponseType = HttpResponse<ITickOption>;
export type EntityArrayResponseType = HttpResponse<ITickOption[]>;

@Injectable({ providedIn: 'root' })
export class TickOptionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tick-options');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(tickOption: NewTickOption): Observable<EntityResponseType> {
    return this.http.post<ITickOption>(this.resourceUrl, tickOption, { observe: 'response' });
  }

  update(tickOption: ITickOption): Observable<EntityResponseType> {
    return this.http.put<ITickOption>(`${this.resourceUrl}/${this.getTickOptionIdentifier(tickOption)}`, tickOption, {
      observe: 'response',
    });
  }

  partialUpdate(tickOption: PartialUpdateTickOption): Observable<EntityResponseType> {
    return this.http.patch<ITickOption>(`${this.resourceUrl}/${this.getTickOptionIdentifier(tickOption)}`, tickOption, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITickOption>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITickOption[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTickOptionIdentifier(tickOption: Pick<ITickOption, 'id'>): number {
    return tickOption.id;
  }

  compareTickOption(o1: Pick<ITickOption, 'id'> | null, o2: Pick<ITickOption, 'id'> | null): boolean {
    return o1 && o2 ? this.getTickOptionIdentifier(o1) === this.getTickOptionIdentifier(o2) : o1 === o2;
  }

  addTickOptionToCollectionIfMissing<Type extends Pick<ITickOption, 'id'>>(
    tickOptionCollection: Type[],
    ...tickOptionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tickOptions: Type[] = tickOptionsToCheck.filter(isPresent);
    if (tickOptions.length > 0) {
      const tickOptionCollectionIdentifiers = tickOptionCollection.map(tickOptionItem => this.getTickOptionIdentifier(tickOptionItem)!);
      const tickOptionsToAdd = tickOptions.filter(tickOptionItem => {
        const tickOptionIdentifier = this.getTickOptionIdentifier(tickOptionItem);
        if (tickOptionCollectionIdentifiers.includes(tickOptionIdentifier)) {
          return false;
        }
        tickOptionCollectionIdentifiers.push(tickOptionIdentifier);
        return true;
      });
      return [...tickOptionsToAdd, ...tickOptionCollection];
    }
    return tickOptionCollection;
  }
}
