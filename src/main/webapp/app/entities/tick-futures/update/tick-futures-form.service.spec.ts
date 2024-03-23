import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tick-futures.test-samples';

import { TickFuturesFormService } from './tick-futures-form.service';

describe('TickFutures Form Service', () => {
  let service: TickFuturesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TickFuturesFormService);
  });

  describe('Service methods', () => {
    describe('createTickFuturesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTickFuturesFormGroup();

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

      it('passing ITickFutures should create a new form with FormGroup', () => {
        const formGroup = service.createTickFuturesFormGroup(sampleWithRequiredData);

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

    describe('getTickFutures', () => {
      it('should return NewTickFutures for default TickFutures initial value', () => {
        const formGroup = service.createTickFuturesFormGroup(sampleWithNewData);

        const tickFutures = service.getTickFutures(formGroup) as any;

        expect(tickFutures).toMatchObject(sampleWithNewData);
      });

      it('should return NewTickFutures for empty TickFutures initial value', () => {
        const formGroup = service.createTickFuturesFormGroup();

        const tickFutures = service.getTickFutures(formGroup) as any;

        expect(tickFutures).toMatchObject({});
      });

      it('should return ITickFutures', () => {
        const formGroup = service.createTickFuturesFormGroup(sampleWithRequiredData);

        const tickFutures = service.getTickFutures(formGroup) as any;

        expect(tickFutures).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITickFutures should not enable id FormControl', () => {
        const formGroup = service.createTickFuturesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTickFutures should disable id FormControl', () => {
        const formGroup = service.createTickFuturesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
