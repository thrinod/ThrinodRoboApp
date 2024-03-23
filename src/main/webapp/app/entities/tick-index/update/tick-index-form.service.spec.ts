import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tick-index.test-samples';

import { TickIndexFormService } from './tick-index-form.service';

describe('TickIndex Form Service', () => {
  let service: TickIndexFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TickIndexFormService);
  });

  describe('Service methods', () => {
    describe('createTickIndexFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTickIndexFormGroup();

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

      it('passing ITickIndex should create a new form with FormGroup', () => {
        const formGroup = service.createTickIndexFormGroup(sampleWithRequiredData);

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

    describe('getTickIndex', () => {
      it('should return NewTickIndex for default TickIndex initial value', () => {
        const formGroup = service.createTickIndexFormGroup(sampleWithNewData);

        const tickIndex = service.getTickIndex(formGroup) as any;

        expect(tickIndex).toMatchObject(sampleWithNewData);
      });

      it('should return NewTickIndex for empty TickIndex initial value', () => {
        const formGroup = service.createTickIndexFormGroup();

        const tickIndex = service.getTickIndex(formGroup) as any;

        expect(tickIndex).toMatchObject({});
      });

      it('should return ITickIndex', () => {
        const formGroup = service.createTickIndexFormGroup(sampleWithRequiredData);

        const tickIndex = service.getTickIndex(formGroup) as any;

        expect(tickIndex).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITickIndex should not enable id FormControl', () => {
        const formGroup = service.createTickIndexFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTickIndex should disable id FormControl', () => {
        const formGroup = service.createTickIndexFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
