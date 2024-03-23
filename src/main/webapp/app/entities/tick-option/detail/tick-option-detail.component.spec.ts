import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TickOptionDetailComponent } from './tick-option-detail.component';

describe('TickOption Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TickOptionDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TickOptionDetailComponent,
              resolve: { tickOption: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TickOptionDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load tickOption on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TickOptionDetailComponent);

      // THEN
      expect(instance.tickOption).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
