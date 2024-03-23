import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TickIndexDetailComponent } from './tick-index-detail.component';

describe('TickIndex Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TickIndexDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TickIndexDetailComponent,
              resolve: { tickIndex: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TickIndexDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load tickIndex on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TickIndexDetailComponent);

      // THEN
      expect(instance.tickIndex).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
