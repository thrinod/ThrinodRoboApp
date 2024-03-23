import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TickFuturesService } from '../service/tick-futures.service';
import { ITickFutures } from '../tick-futures.model';
import { TickFuturesFormService } from './tick-futures-form.service';

import { TickFuturesUpdateComponent } from './tick-futures-update.component';

describe('TickFutures Management Update Component', () => {
  let comp: TickFuturesUpdateComponent;
  let fixture: ComponentFixture<TickFuturesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tickFuturesFormService: TickFuturesFormService;
  let tickFuturesService: TickFuturesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TickFuturesUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TickFuturesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TickFuturesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tickFuturesFormService = TestBed.inject(TickFuturesFormService);
    tickFuturesService = TestBed.inject(TickFuturesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tickFutures: ITickFutures = { id: 456 };

      activatedRoute.data = of({ tickFutures });
      comp.ngOnInit();

      expect(comp.tickFutures).toEqual(tickFutures);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickFutures>>();
      const tickFutures = { id: 123 };
      jest.spyOn(tickFuturesFormService, 'getTickFutures').mockReturnValue(tickFutures);
      jest.spyOn(tickFuturesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickFutures });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tickFutures }));
      saveSubject.complete();

      // THEN
      expect(tickFuturesFormService.getTickFutures).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tickFuturesService.update).toHaveBeenCalledWith(expect.objectContaining(tickFutures));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickFutures>>();
      const tickFutures = { id: 123 };
      jest.spyOn(tickFuturesFormService, 'getTickFutures').mockReturnValue({ id: null });
      jest.spyOn(tickFuturesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickFutures: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tickFutures }));
      saveSubject.complete();

      // THEN
      expect(tickFuturesFormService.getTickFutures).toHaveBeenCalled();
      expect(tickFuturesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickFutures>>();
      const tickFutures = { id: 123 };
      jest.spyOn(tickFuturesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickFutures });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tickFuturesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
