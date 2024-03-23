import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITickIndex, NewTickIndex } from '../tick-index.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITickIndex for edit and NewTickIndexFormGroupInput for create.
 */
type TickIndexFormGroupInput = ITickIndex | PartialWithRequiredKeyOf<NewTickIndex>;

type TickIndexFormDefaults = Pick<NewTickIndex, 'id'>;

type TickIndexFormGroupContent = {
  id: FormControl<ITickIndex['id'] | NewTickIndex['id']>;
  instrumentKey: FormControl<ITickIndex['instrumentKey']>;
  exchangeToken: FormControl<ITickIndex['exchangeToken']>;
  tradingSymbol: FormControl<ITickIndex['tradingSymbol']>;
  name: FormControl<ITickIndex['name']>;
  lastPrice: FormControl<ITickIndex['lastPrice']>;
  expiry: FormControl<ITickIndex['expiry']>;
  strike: FormControl<ITickIndex['strike']>;
  tickSize: FormControl<ITickIndex['tickSize']>;
  lotSize: FormControl<ITickIndex['lotSize']>;
  instrumentType: FormControl<ITickIndex['instrumentType']>;
  optionType: FormControl<ITickIndex['optionType']>;
  exchange: FormControl<ITickIndex['exchange']>;
};

export type TickIndexFormGroup = FormGroup<TickIndexFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TickIndexFormService {
  createTickIndexFormGroup(tickIndex: TickIndexFormGroupInput = { id: null }): TickIndexFormGroup {
    const tickIndexRawValue = {
      ...this.getFormDefaults(),
      ...tickIndex,
    };
    return new FormGroup<TickIndexFormGroupContent>({
      id: new FormControl(
        { value: tickIndexRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      instrumentKey: new FormControl(tickIndexRawValue.instrumentKey),
      exchangeToken: new FormControl(tickIndexRawValue.exchangeToken),
      tradingSymbol: new FormControl(tickIndexRawValue.tradingSymbol),
      name: new FormControl(tickIndexRawValue.name),
      lastPrice: new FormControl(tickIndexRawValue.lastPrice),
      expiry: new FormControl(tickIndexRawValue.expiry),
      strike: new FormControl(tickIndexRawValue.strike),
      tickSize: new FormControl(tickIndexRawValue.tickSize),
      lotSize: new FormControl(tickIndexRawValue.lotSize),
      instrumentType: new FormControl(tickIndexRawValue.instrumentType),
      optionType: new FormControl(tickIndexRawValue.optionType),
      exchange: new FormControl(tickIndexRawValue.exchange),
    });
  }

  getTickIndex(form: TickIndexFormGroup): ITickIndex | NewTickIndex {
    return form.getRawValue() as ITickIndex | NewTickIndex;
  }

  resetForm(form: TickIndexFormGroup, tickIndex: TickIndexFormGroupInput): void {
    const tickIndexRawValue = { ...this.getFormDefaults(), ...tickIndex };
    form.reset(
      {
        ...tickIndexRawValue,
        id: { value: tickIndexRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TickIndexFormDefaults {
    return {
      id: null,
    };
  }
}
