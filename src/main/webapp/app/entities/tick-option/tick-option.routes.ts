import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TickOptionComponent } from './list/tick-option.component';
import { TickOptionDetailComponent } from './detail/tick-option-detail.component';
import { TickOptionUpdateComponent } from './update/tick-option-update.component';
import TickOptionResolve from './route/tick-option-routing-resolve.service';

const tickOptionRoute: Routes = [
  {
    path: '',
    component: TickOptionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TickOptionDetailComponent,
    resolve: {
      tickOption: TickOptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TickOptionUpdateComponent,
    resolve: {
      tickOption: TickOptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TickOptionUpdateComponent,
    resolve: {
      tickOption: TickOptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tickOptionRoute;
