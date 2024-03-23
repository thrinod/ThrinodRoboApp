import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TickIndexComponent } from './list/tick-index.component';
import { TickIndexDetailComponent } from './detail/tick-index-detail.component';
import { TickIndexUpdateComponent } from './update/tick-index-update.component';
import TickIndexResolve from './route/tick-index-routing-resolve.service';

const tickIndexRoute: Routes = [
  {
    path: '',
    component: TickIndexComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TickIndexDetailComponent,
    resolve: {
      tickIndex: TickIndexResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TickIndexUpdateComponent,
    resolve: {
      tickIndex: TickIndexResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TickIndexUpdateComponent,
    resolve: {
      tickIndex: TickIndexResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tickIndexRoute;
