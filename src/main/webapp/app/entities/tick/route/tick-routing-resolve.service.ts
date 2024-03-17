import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITICK } from '../tick.model';
import { TICKService } from '../service/tick.service';

export const tICKResolve = (route: ActivatedRouteSnapshot): Observable<null | ITICK> => {
  const id = route.params['id'];
  if (id) {
    return inject(TICKService)
      .find(id)
      .pipe(
        mergeMap((tICK: HttpResponse<ITICK>) => {
          if (tICK.body) {
            return of(tICK.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tICKResolve;
