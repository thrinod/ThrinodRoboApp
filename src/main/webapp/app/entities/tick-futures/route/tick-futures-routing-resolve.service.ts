import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITickFutures } from '../tick-futures.model';
import { TickFuturesService } from '../service/tick-futures.service';

export const tickFuturesResolve = (route: ActivatedRouteSnapshot): Observable<null | ITickFutures> => {
  const id = route.params['id'];
  if (id) {
    return inject(TickFuturesService)
      .find(id)
      .pipe(
        mergeMap((tickFutures: HttpResponse<ITickFutures>) => {
          if (tickFutures.body) {
            return of(tickFutures.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tickFuturesResolve;
