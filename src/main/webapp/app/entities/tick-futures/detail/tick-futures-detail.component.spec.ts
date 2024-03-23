import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TickFuturesDetailComponent } from './tick-futures-detail.component';

describe('TickFutures Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TickFuturesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TickFuturesDetailComponent,
              resolve: { tickFutures: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TickFuturesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load tickFutures on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TickFuturesDetailComponent);

      // THEN
      expect(instance.tickFutures).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
