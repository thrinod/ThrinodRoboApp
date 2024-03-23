import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITickOption } from '../tick-option.model';
import { TickOptionService } from '../service/tick-option.service';

export const tickOptionResolve = (route: ActivatedRouteSnapshot): Observable<null | ITickOption> => {
  const id = route.params['id'];
  if (id) {
    return inject(TickOptionService)
      .find(id)
      .pipe(
        mergeMap((tickOption: HttpResponse<ITickOption>) => {
          if (tickOption.body) {
            return of(tickOption.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tickOptionResolve;
