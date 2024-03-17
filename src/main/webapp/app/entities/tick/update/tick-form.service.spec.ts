import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tick.test-samples';

import { TICKFormService } from './tick-form.service';

describe('TICK Form Service', () => {
  let service: TICKFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TICKFormService);
  });

  describe('Service methods', () => {
    describe('createTICKFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTICKFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            instrumentKey: expect.any(Object),
            exchangeToken: expect.any(Object),
            tradingSymbol: expect.any(Object),
            name: expect.any(Object),
            lastPrice: expect.any(Object),
            expiry: expect.any(Object),
            strike: expect.any(Object),
            tickSize: expect.any(Object),
            lotSize: expect.any(Object),
            instrumentType: expect.any(Object),
            optionType: expect.any(Object),
            exchange: expect.any(Object),
          }),
        );
      });

      it('passing ITICK should create a new form with FormGroup', () => {
        const formGroup = service.createTICKFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            instrumentKey: expect.any(Object),
            exchangeToken: expect.any(Object),
            tradingSymbol: expect.any(Object),
            name: expect.any(Object),
            lastPrice: expect.any(Object),
            expiry: expect.any(Object),
            strike: expect.any(Object),
            tickSize: expect.any(Object),
            lotSize: expect.any(Object),
            instrumentType: expect.any(Object),
            optionType: expect.any(Object),
            exchange: expect.any(Object),
          }),
        );
      });
    });

    describe('getTICK', () => {
      it('should return NewTICK for default TICK initial value', () => {
        const formGroup = service.createTICKFormGroup(sampleWithNewData);

        const tICK = service.getTICK(formGroup) as any;

        expect(tICK).toMatchObject(sampleWithNewData);
      });

      it('should return NewTICK for empty TICK initial value', () => {
        const formGroup = service.createTICKFormGroup();

        const tICK = service.getTICK(formGroup) as any;

        expect(tICK).toMatchObject({});
      });

      it('should return ITICK', () => {
        const formGroup = service.createTICKFormGroup(sampleWithRequiredData);

        const tICK = service.getTICK(formGroup) as any;

        expect(tICK).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITICK should not enable id FormControl', () => {
        const formGroup = service.createTICKFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTICK should disable id FormControl', () => {
        const formGroup = service.createTICKFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
