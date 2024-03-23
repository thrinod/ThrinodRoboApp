import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tick-stock.test-samples';

import { TickStockFormService } from './tick-stock-form.service';

describe('TickStock Form Service', () => {
  let service: TickStockFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TickStockFormService);
  });

  describe('Service methods', () => {
    describe('createTickStockFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTickStockFormGroup();

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

      it('passing ITickStock should create a new form with FormGroup', () => {
        const formGroup = service.createTickStockFormGroup(sampleWithRequiredData);

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

    describe('getTickStock', () => {
      it('should return NewTickStock for default TickStock initial value', () => {
        const formGroup = service.createTickStockFormGroup(sampleWithNewData);

        const tickStock = service.getTickStock(formGroup) as any;

        expect(tickStock).toMatchObject(sampleWithNewData);
      });

      it('should return NewTickStock for empty TickStock initial value', () => {
        const formGroup = service.createTickStockFormGroup();

        const tickStock = service.getTickStock(formGroup) as any;

        expect(tickStock).toMatchObject({});
      });

      it('should return ITickStock', () => {
        const formGroup = service.createTickStockFormGroup(sampleWithRequiredData);

        const tickStock = service.getTickStock(formGroup) as any;

        expect(tickStock).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITickStock should not enable id FormControl', () => {
        const formGroup = service.createTickStockFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTickStock should disable id FormControl', () => {
        const formGroup = service.createTickStockFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
