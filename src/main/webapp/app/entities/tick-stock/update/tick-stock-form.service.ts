import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITickStock, NewTickStock } from '../tick-stock.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITickStock for edit and NewTickStockFormGroupInput for create.
 */
type TickStockFormGroupInput = ITickStock | PartialWithRequiredKeyOf<NewTickStock>;

type TickStockFormDefaults = Pick<NewTickStock, 'id'>;

type TickStockFormGroupContent = {
  id: FormControl<ITickStock['id'] | NewTickStock['id']>;
  instrumentKey: FormControl<ITickStock['instrumentKey']>;
  exchangeToken: FormControl<ITickStock['exchangeToken']>;
  tradingSymbol: FormControl<ITickStock['tradingSymbol']>;
  name: FormControl<ITickStock['name']>;
  lastPrice: FormControl<ITickStock['lastPrice']>;
  expiry: FormControl<ITickStock['expiry']>;
  strike: FormControl<ITickStock['strike']>;
  tickSize: FormControl<ITickStock['tickSize']>;
  lotSize: FormControl<ITickStock['lotSize']>;
  instrumentType: FormControl<ITickStock['instrumentType']>;
  optionType: FormControl<ITickStock['optionType']>;
  exchange: FormControl<ITickStock['exchange']>;
};

export type TickStockFormGroup = FormGroup<TickStockFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TickStockFormService {
  createTickStockFormGroup(tickStock: TickStockFormGroupInput = { id: null }): TickStockFormGroup {
    const tickStockRawValue = {
      ...this.getFormDefaults(),
      ...tickStock,
    };
    return new FormGroup<TickStockFormGroupContent>({
      id: new FormControl(
        { value: tickStockRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      instrumentKey: new FormControl(tickStockRawValue.instrumentKey),
      exchangeToken: new FormControl(tickStockRawValue.exchangeToken),
      tradingSymbol: new FormControl(tickStockRawValue.tradingSymbol),
      name: new FormControl(tickStockRawValue.name),
      lastPrice: new FormControl(tickStockRawValue.lastPrice),
      expiry: new FormControl(tickStockRawValue.expiry),
      strike: new FormControl(tickStockRawValue.strike),
      tickSize: new FormControl(tickStockRawValue.tickSize),
      lotSize: new FormControl(tickStockRawValue.lotSize),
      instrumentType: new FormControl(tickStockRawValue.instrumentType),
      optionType: new FormControl(tickStockRawValue.optionType),
      exchange: new FormControl(tickStockRawValue.exchange),
    });
  }

  getTickStock(form: TickStockFormGroup): ITickStock | NewTickStock {
    return form.getRawValue() as ITickStock | NewTickStock;
  }

  resetForm(form: TickStockFormGroup, tickStock: TickStockFormGroupInput): void {
    const tickStockRawValue = { ...this.getFormDefaults(), ...tickStock };
    form.reset(
      {
        ...tickStockRawValue,
        id: { value: tickStockRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TickStockFormDefaults {
    return {
      id: null,
    };
  }
}
