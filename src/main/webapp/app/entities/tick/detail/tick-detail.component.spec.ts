import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TICKDetailComponent } from './tick-detail.component';

describe('TICK Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TICKDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TICKDetailComponent,
              resolve: { tICK: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TICKDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load tICK on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TICKDetailComponent);

      // THEN
      expect(instance.tICK).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
