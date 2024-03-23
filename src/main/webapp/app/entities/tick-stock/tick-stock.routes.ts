import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TickStockComponent } from './list/tick-stock.component';
import { TickStockDetailComponent } from './detail/tick-stock-detail.component';
import { TickStockUpdateComponent } from './update/tick-stock-update.component';
import TickStockResolve from './route/tick-stock-routing-resolve.service';

const tickStockRoute: Routes = [
  {
    path: '',
    component: TickStockComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TickStockDetailComponent,
    resolve: {
      tickStock: TickStockResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TickStockUpdateComponent,
    resolve: {
      tickStock: TickStockResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TickStockUpdateComponent,
    resolve: {
      tickStock: TickStockResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tickStockRoute;
