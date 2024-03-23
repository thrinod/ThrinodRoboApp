import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TickStockDetailComponent } from './tick-stock-detail.component';

describe('TickStock Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TickStockDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TickStockDetailComponent,
              resolve: { tickStock: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TickStockDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load tickStock on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TickStockDetailComponent);

      // THEN
      expect(instance.tickStock).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
