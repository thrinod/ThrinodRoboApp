import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITICK, NewTICK } from '../tick.model';

export type PartialUpdateTICK = Partial<ITICK> & Pick<ITICK, 'id'>;

export type EntityResponseType = HttpResponse<ITICK>;
export type EntityArrayResponseType = HttpResponse<ITICK[]>;

@Injectable({ providedIn: 'root' })
export class TICKService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ticks');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(tICK: NewTICK): Observable<EntityResponseType> {
    return this.http.post<ITICK>(this.resourceUrl, tICK, { observe: 'response' });
  }

  update(tICK: ITICK): Observable<EntityResponseType> {
    return this.http.put<ITICK>(`${this.resourceUrl}/${this.getTICKIdentifier(tICK)}`, tICK, { observe: 'response' });
  }

  partialUpdate(tICK: PartialUpdateTICK): Observable<EntityResponseType> {
    return this.http.patch<ITICK>(`${this.resourceUrl}/${this.getTICKIdentifier(tICK)}`, tICK, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITICK>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITICK[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTICKIdentifier(tICK: Pick<ITICK, 'id'>): number {
    return tICK.id;
  }

  compareTICK(o1: Pick<ITICK, 'id'> | null, o2: Pick<ITICK, 'id'> | null): boolean {
    return o1 && o2 ? this.getTICKIdentifier(o1) === this.getTICKIdentifier(o2) : o1 === o2;
  }

  addTICKToCollectionIfMissing<Type extends Pick<ITICK, 'id'>>(
    tICKCollection: Type[],
    ...tICKSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tICKS: Type[] = tICKSToCheck.filter(isPresent);
    if (tICKS.length > 0) {
      const tICKCollectionIdentifiers = tICKCollection.map(tICKItem => this.getTICKIdentifier(tICKItem)!);
      const tICKSToAdd = tICKS.filter(tICKItem => {
        const tICKIdentifier = this.getTICKIdentifier(tICKItem);
        if (tICKCollectionIdentifiers.includes(tICKIdentifier)) {
          return false;
        }
        tICKCollectionIdentifiers.push(tICKIdentifier);
        return true;
      });
      return [...tICKSToAdd, ...tICKCollection];
    }
    return tICKCollection;
  }
}
