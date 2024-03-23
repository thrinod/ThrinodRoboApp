import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TickStockService } from '../service/tick-stock.service';
import { ITickStock } from '../tick-stock.model';
import { TickStockFormService } from './tick-stock-form.service';

import { TickStockUpdateComponent } from './tick-stock-update.component';

describe('TickStock Management Update Component', () => {
  let comp: TickStockUpdateComponent;
  let fixture: ComponentFixture<TickStockUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tickStockFormService: TickStockFormService;
  let tickStockService: TickStockService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TickStockUpdateComponent],
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
      .overrideTemplate(TickStockUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TickStockUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tickStockFormService = TestBed.inject(TickStockFormService);
    tickStockService = TestBed.inject(TickStockService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tickStock: ITickStock = { id: 456 };

      activatedRoute.data = of({ tickStock });
      comp.ngOnInit();

      expect(comp.tickStock).toEqual(tickStock);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickStock>>();
      const tickStock = { id: 123 };
      jest.spyOn(tickStockFormService, 'getTickStock').mockReturnValue(tickStock);
      jest.spyOn(tickStockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickStock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tickStock }));
      saveSubject.complete();

      // THEN
      expect(tickStockFormService.getTickStock).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tickStockService.update).toHaveBeenCalledWith(expect.objectContaining(tickStock));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickStock>>();
      const tickStock = { id: 123 };
      jest.spyOn(tickStockFormService, 'getTickStock').mockReturnValue({ id: null });
      jest.spyOn(tickStockService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickStock: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tickStock }));
      saveSubject.complete();

      // THEN
      expect(tickStockFormService.getTickStock).toHaveBeenCalled();
      expect(tickStockService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITickStock>>();
      const tickStock = { id: 123 };
      jest.spyOn(tickStockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tickStock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tickStockService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
