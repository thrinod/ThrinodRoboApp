import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TickFuturesComponent } from './list/tick-futures.component';
import { TickFuturesDetailComponent } from './detail/tick-futures-detail.component';
import { TickFuturesUpdateComponent } from './update/tick-futures-update.component';
import TickFuturesResolve from './route/tick-futures-routing-resolve.service';

const tickFuturesRoute: Routes = [
  {
    path: '',
    component: TickFuturesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TickFuturesDetailComponent,
    resolve: {
      tickFutures: TickFuturesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TickFuturesUpdateComponent,
    resolve: {
      tickFutures: TickFuturesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TickFuturesUpdateComponent,
    resolve: {
      tickFutures: TickFuturesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tickFuturesRoute;
