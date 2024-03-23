import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TickIndexService } from '../service/tick-index.service';
import { ITickIndex } from '../tick-index.model';
import { TickIndexFormService } from './tick-index-form.service';

import { TickIndexUpdateComponent } from './tick-index-update.component';

describe('TickIndex Management Update Component', () => {
  let comp: TickIndexUpdateComponent;
  let fixture: ComponentFixture<TickIndexUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tickIndexFormService: TickIndexFormService;
  let tickIndexService: TickIndexService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TickIndexUpdateComponent],
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
      .overrideTemplate(TickIndexUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TickIndexUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tickIndexFormService = TestBed.inject(TickIndexFormService);
    tickIndexService = TestBed.inject(TickIndexService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tickIndex: ITickIndex = { id: 456 };

      activatedRoute.data = of({ tickIndex });
      comp.ngOnInit();

      expect(comp.tickIndex).toEqual(tickIndex);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickIndex>>();
      const tickIndex = { id: 123 };
      jest.spyOn(tickIndexFormService, 'getTickIndex').mockReturnValue(tickIndex);
      jest.spyOn(tickIndexService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickIndex });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tickIndex }));
      saveSubject.complete();

      // THEN
      expect(tickIndexFormService.getTickIndex).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tickIndexService.update).toHaveBeenCalledWith(expect.objectContaining(tickIndex));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickIndex>>();
      const tickIndex = { id: 123 };
      jest.spyOn(tickIndexFormService, 'getTickIndex').mockReturnValue({ id: null });
      jest.spyOn(tickIndexService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickIndex: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tickIndex }));
      saveSubject.complete();

      // THEN
      expect(tickIndexFormService.getTickIndex).toHaveBeenCalled();
      expect(tickIndexService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickIndex>>();
      const tickIndex = { id: 123 };
      jest.spyOn(tickIndexService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickIndex });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tickIndexService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
