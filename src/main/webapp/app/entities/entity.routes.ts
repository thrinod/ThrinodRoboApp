import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'tick',
    data: { pageTitle: 'TICKS' },
    loadChildren: () => import('./tick/tick.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
