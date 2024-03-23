import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITickFutures, NewTickFutures } from '../tick-futures.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITickFutures for edit and NewTickFuturesFormGroupInput for create.
 */
type TickFuturesFormGroupInput = ITickFutures | PartialWithRequiredKeyOf<NewTickFutures>;

type TickFuturesFormDefaults = Pick<NewTickFutures, 'id'>;

type TickFuturesFormGroupContent = {
  id: FormControl<ITickFutures['id'] | NewTickFutures['id']>;
  instrumentKey: FormControl<ITickFutures['instrumentKey']>;
  exchangeToken: FormControl<ITickFutures['exchangeToken']>;
  tradingSymbol: FormControl<ITickFutures['tradingSymbol']>;
  name: FormControl<ITickFutures['name']>;
  lastPrice: FormControl<ITickFutures['lastPrice']>;
  expiry: FormControl<ITickFutures['expiry']>;
  strike: FormControl<ITickFutures['strike']>;
  tickSize: FormControl<ITickFutures['tickSize']>;
  lotSize: FormControl<ITickFutures['lotSize']>;
  instrumentType: FormControl<ITickFutures['instrumentType']>;
  optionType: FormControl<ITickFutures['optionType']>;
  exchange: FormControl<ITickFutures['exchange']>;
};

export type TickFuturesFormGroup = FormGroup<TickFuturesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TickFuturesFormService {
  createTickFuturesFormGroup(tickFutures: TickFuturesFormGroupInput = { id: null }): TickFuturesFormGroup {
    const tickFuturesRawValue = {
      ...this.getFormDefaults(),
      ...tickFutures,
    };
    return new FormGroup<TickFuturesFormGroupContent>({
      id: new FormControl(
        { value: tickFuturesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      instrumentKey: new FormControl(tickFuturesRawValue.instrumentKey),
      exchangeToken: new FormControl(tickFuturesRawValue.exchangeToken),
      tradingSymbol: new FormControl(tickFuturesRawValue.tradingSymbol),
      name: new FormControl(tickFuturesRawValue.name),
      lastPrice: new FormControl(tickFuturesRawValue.lastPrice),
      expiry: new FormControl(tickFuturesRawValue.expiry),
      strike: new FormControl(tickFuturesRawValue.strike),
      tickSize: new FormControl(tickFuturesRawValue.tickSize),
      lotSize: new FormControl(tickFuturesRawValue.lotSize),
      instrumentType: new FormControl(tickFuturesRawValue.instrumentType),
      optionType: new FormControl(tickFuturesRawValue.optionType),
      exchange: new FormControl(tickFuturesRawValue.exchange),
    });
  }

  getTickFutures(form: TickFuturesFormGroup): ITickFutures | NewTickFutures {
    return form.getRawValue() as ITickFutures | NewTickFutures;
  }

  resetForm(form: TickFuturesFormGroup, tickFutures: TickFuturesFormGroupInput): void {
    const tickFuturesRawValue = { ...this.getFormDefaults(), ...tickFutures };
    form.reset(
      {
        ...tickFuturesRawValue,
        id: { value: tickFuturesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TickFuturesFormDefaults {
    return {
      id: null,
    };
  }
}
