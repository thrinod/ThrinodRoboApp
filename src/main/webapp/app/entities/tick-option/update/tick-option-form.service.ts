import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITickOption, NewTickOption } from '../tick-option.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITickOption for edit and NewTickOptionFormGroupInput for create.
 */
type TickOptionFormGroupInput = ITickOption | PartialWithRequiredKeyOf<NewTickOption>;

type TickOptionFormDefaults = Pick<NewTickOption, 'id'>;

type TickOptionFormGroupContent = {
  id: FormControl<ITickOption['id'] | NewTickOption['id']>;
  instrumentKey: FormControl<ITickOption['instrumentKey']>;
  exchangeToken: FormControl<ITickOption['exchangeToken']>;
  tradingSymbol: FormControl<ITickOption['tradingSymbol']>;
  name: FormControl<ITickOption['name']>;
  lastPrice: FormControl<ITickOption['lastPrice']>;
  expiry: FormControl<ITickOption['expiry']>;
  strike: FormControl<ITickOption['strike']>;
  tickSize: FormControl<ITickOption['tickSize']>;
  lotSize: FormControl<ITickOption['lotSize']>;
  instrumentType: FormControl<ITickOption['instrumentType']>;
  optionType: FormControl<ITickOption['optionType']>;
  exchange: FormControl<ITickOption['exchange']>;
};

export type TickOptionFormGroup = FormGroup<TickOptionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TickOptionFormService {
  createTickOptionFormGroup(tickOption: TickOptionFormGroupInput = { id: null }): TickOptionFormGroup {
    const tickOptionRawValue = {
      ...this.getFormDefaults(),
      ...tickOption,
    };
    return new FormGroup<TickOptionFormGroupContent>({
      id: new FormControl(
        { value: tickOptionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      instrumentKey: new FormControl(tickOptionRawValue.instrumentKey),
      exchangeToken: new FormControl(tickOptionRawValue.exchangeToken),
      tradingSymbol: new FormControl(tickOptionRawValue.tradingSymbol),
      name: new FormControl(tickOptionRawValue.name),
      lastPrice: new FormControl(tickOptionRawValue.lastPrice),
      expiry: new FormControl(tickOptionRawValue.expiry),
      strike: new FormControl(tickOptionRawValue.strike),
      tickSize: new FormControl(tickOptionRawValue.tickSize),
      lotSize: new FormControl(tickOptionRawValue.lotSize),
      instrumentType: new FormControl(tickOptionRawValue.instrumentType),
      optionType: new FormControl(tickOptionRawValue.optionType),
      exchange: new FormControl(tickOptionRawValue.exchange),
    });
  }

  getTickOption(form: TickOptionFormGroup): ITickOption | NewTickOption {
    return form.getRawValue() as ITickOption | NewTickOption;
  }

  resetForm(form: TickOptionFormGroup, tickOption: TickOptionFormGroupInput): void {
    const tickOptionRawValue = { ...this.getFormDefaults(), ...tickOption };
    form.reset(
      {
        ...tickOptionRawValue,
        id: { value: tickOptionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TickOptionFormDefaults {
    return {
      id: null,
    };
  }
}
