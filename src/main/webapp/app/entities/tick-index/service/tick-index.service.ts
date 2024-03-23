import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITickIndex, NewTickIndex } from '../tick-index.model';

export type PartialUpdateTickIndex = Partial<ITickIndex> & Pick<ITickIndex, 'id'>;

export type EntityResponseType = HttpResponse<ITickIndex>;
export type EntityArrayResponseType = HttpResponse<ITickIndex[]>;

@Injectable({ providedIn: 'root' })
export class TickIndexService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tick-indices');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(tickIndex: NewTickIndex): Observable<EntityResponseType> {
    return this.http.post<ITickIndex>(this.resourceUrl, tickIndex, { observe: 'response' });
  }

  update(tickIndex: ITickIndex): Observable<EntityResponseType> {
    return this.http.put<ITickIndex>(`${this.resourceUrl}/${this.getTickIndexIdentifier(tickIndex)}`, tickIndex, { observe: 'response' });
  }

  partialUpdate(tickIndex: PartialUpdateTickIndex): Observable<EntityResponseType> {
    return this.http.patch<ITickIndex>(`${this.resourceUrl}/${this.getTickIndexIdentifier(tickIndex)}`, tickIndex, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITickIndex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITickIndex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTickIndexIdentifier(tickIndex: Pick<ITickIndex, 'id'>): number {
    return tickIndex.id;
  }

  compareTickIndex(o1: Pick<ITickIndex, 'id'> | null, o2: Pick<ITickIndex, 'id'> | null): boolean {
    return o1 && o2 ? this.getTickIndexIdentifier(o1) === this.getTickIndexIdentifier(o2) : o1 === o2;
  }

  addTickIndexToCollectionIfMissing<Type extends Pick<ITickIndex, 'id'>>(
    tickIndexCollection: Type[],
    ...tickIndicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tickIndices: Type[] = tickIndicesToCheck.filter(isPresent);
    if (tickIndices.length > 0) {
      const tickIndexCollectionIdentifiers = tickIndexCollection.map(tickIndexItem => this.getTickIndexIdentifier(tickIndexItem)!);
      const tickIndicesToAdd = tickIndices.filter(tickIndexItem => {
        const tickIndexIdentifier = this.getTickIndexIdentifier(tickIndexItem);
        if (tickIndexCollectionIdentifiers.includes(tickIndexIdentifier)) {
          return false;
        }
        tickIndexCollectionIdentifiers.push(tickIndexIdentifier);
        return true;
      });
      return [...tickIndicesToAdd, ...tickIndexCollection];
    }
    return tickIndexCollection;
  }
}
