import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TickOptionService } from '../service/tick-option.service';
import { ITickOption } from '../tick-option.model';
import { TickOptionFormService } from './tick-option-form.service';

import { TickOptionUpdateComponent } from './tick-option-update.component';

describe('TickOption Management Update Component', () => {
  let comp: TickOptionUpdateComponent;
  let fixture: ComponentFixture<TickOptionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tickOptionFormService: TickOptionFormService;
  let tickOptionService: TickOptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TickOptionUpdateComponent],
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
      .overrideTemplate(TickOptionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TickOptionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tickOptionFormService = TestBed.inject(TickOptionFormService);
    tickOptionService = TestBed.inject(TickOptionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tickOption: ITickOption = { id: 456 };

      activatedRoute.data = of({ tickOption });
      comp.ngOnInit();

      expect(comp.tickOption).toEqual(tickOption);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickOption>>();
      const tickOption = { id: 123 };
      jest.spyOn(tickOptionFormService, 'getTickOption').mockReturnValue(tickOption);
      jest.spyOn(tickOptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickOption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tickOption }));
      saveSubject.complete();

      // THEN
      expect(tickOptionFormService.getTickOption).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tickOptionService.update).toHaveBeenCalledWith(expect.objectContaining(tickOption));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickOption>>();
      const tickOption = { id: 123 };
      jest.spyOn(tickOptionFormService, 'getTickOption').mockReturnValue({ id: null });
      jest.spyOn(tickOptionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickOption: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tickOption }));
      saveSubject.complete();

      // THEN
      expect(tickOptionFormService.getTickOption).toHaveBeenCalled();
      expect(tickOptionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickOption>>();
      const tickOption = { id: 123 };
      jest.spyOn(tickOptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickOption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tickOptionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
