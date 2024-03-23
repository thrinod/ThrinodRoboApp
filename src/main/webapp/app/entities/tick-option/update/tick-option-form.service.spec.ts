import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tick-option.test-samples';

import { TickOptionFormService } from './tick-option-form.service';

describe('TickOption Form Service', () => {
  let service: TickOptionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TickOptionFormService);
  });

  describe('Service methods', () => {
    describe('createTickOptionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTickOptionFormGroup();

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

      it('passing ITickOption should create a new form with FormGroup', () => {
        const formGroup = service.createTickOptionFormGroup(sampleWithRequiredData);

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

    describe('getTickOption', () => {
      it('should return NewTickOption for default TickOption initial value', () => {
        const formGroup = service.createTickOptionFormGroup(sampleWithNewData);

        const tickOption = service.getTickOption(formGroup) as any;

        expect(tickOption).toMatchObject(sampleWithNewData);
      });

      it('should return NewTickOption for empty TickOption initial value', () => {
        const formGroup = service.createTickOptionFormGroup();

        const tickOption = service.getTickOption(formGroup) as any;

        expect(tickOption).toMatchObject({});
      });

      it('should return ITickOption', () => {
        const formGroup = service.createTickOptionFormGroup(sampleWithRequiredData);

        const tickOption = service.getTickOption(formGroup) as any;

        expect(tickOption).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITickOption should not enable id FormControl', () => {
        const formGroup = service.createTickOptionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTickOption should disable id FormControl', () => {
        const formGroup = service.createTickOptionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
