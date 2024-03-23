import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITickIndex } from '../tick-index.model';
import { TickIndexService } from '../service/tick-index.service';

export const tickIndexResolve = (route: ActivatedRouteSnapshot): Observable<null | ITickIndex> => {
  const id = route.params['id'];
  if (id) {
    return inject(TickIndexService)
      .find(id)
      .pipe(
        mergeMap((tickIndex: HttpResponse<ITickIndex>) => {
          if (tickIndex.body) {
            return of(tickIndex.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tickIndexResolve;
