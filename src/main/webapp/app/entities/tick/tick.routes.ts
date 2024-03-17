import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TICKComponent } from './list/tick.component';
import { TICKDetailComponent } from './detail/tick-detail.component';
import { TICKUpdateComponent } from './update/tick-update.component';
import TICKResolve from './route/tick-routing-resolve.service';

const tICKRoute: Routes = [
  {
    path: '',
    component: TICKComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TICKDetailComponent,
    resolve: {
      tICK: TICKResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TICKUpdateComponent,
    resolve: {
      tICK: TICKResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TICKUpdateComponent,
    resolve: {
      tICK: TICKResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tICKRoute;
