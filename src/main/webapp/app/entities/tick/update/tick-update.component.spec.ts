import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TICKService } from '../service/tick.service';
import { ITICK } from '../tick.model';
import { TICKFormService } from './tick-form.service';

import { TICKUpdateComponent } from './tick-update.component';

describe('TICK Management Update Component', () => {
  let comp: TICKUpdateComponent;
  let fixture: ComponentFixture<TICKUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tICKFormService: TICKFormService;
  let tICKService: TICKService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TICKUpdateComponent],
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
      .overrideTemplate(TICKUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TICKUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tICKFormService = TestBed.inject(TICKFormService);
    tICKService = TestBed.inject(TICKService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tICK: ITICK = { id: 456 };

      activatedRoute.data = of({ tICK });
      comp.ngOnInit();

      expect(comp.tICK).toEqual(tICK);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITICK>>();
      const tICK = { id: 123 };
      jest.spyOn(tICKFormService, 'getTICK').mockReturnValue(tICK);
      jest.spyOn(tICKService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tICK });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tICK }));
      saveSubject.complete();

      // THEN
      expect(tICKFormService.getTICK).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tICKService.update).toHaveBeenCalledWith(expect.objectContaining(tICK));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITICK>>();
      const tICK = { id: 123 };
      jest.spyOn(tICKFormService, 'getTICK').mockReturnValue({ id: null });
      jest.spyOn(tICKService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tICK: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tICK }));
      saveSubject.complete();

      // THEN
      expect(tICKFormService.getTICK).toHaveBeenCalled();
      expect(tICKService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITICK>>();
      const tICK = { id: 123 };
      jest.spyOn(tICKService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tICK });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tICKService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
