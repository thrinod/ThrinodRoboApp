import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITickStock } from '../tick-stock.model';
import { TickStockService } from '../service/tick-stock.service';

export const tickStockResolve = (route: ActivatedRouteSnapshot): Observable<null | ITickStock> => {
  const id = route.params['id'];
  if (id) {
    return inject(TickStockService)
      .find(id)
      .pipe(
        mergeMap((tickStock: HttpResponse<ITickStock>) => {
          if (tickStock.body) {
            return of(tickStock.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tickStockResolve;
