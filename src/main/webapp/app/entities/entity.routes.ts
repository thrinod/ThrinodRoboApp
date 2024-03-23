import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'tick',
    data: { pageTitle: 'TICKS' },
    loadChildren: () => import('./tick/tick.routes'),
  },
  {
    path: 'tick-option',
    data: { pageTitle: 'TickOptions' },
    loadChildren: () => import('./tick-option/tick-option.routes'),
  },
  {
    path: 'tick-stock',
    data: { pageTitle: 'TickStocks' },
    loadChildren: () => import('./tick-stock/tick-stock.routes'),
  },
  {
    path: 'tick-futures',
    data: { pageTitle: 'TickFutures' },
    loadChildren: () => import('./tick-futures/tick-futures.routes'),
  },
  {
    path: 'tick-index',
    data: { pageTitle: 'TickIndices' },
    loadChildren: () => import('./tick-index/tick-index.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
