import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITICK, NewTICK } from '../tick.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITICK for edit and NewTICKFormGroupInput for create.
 */
type TICKFormGroupInput = ITICK | PartialWithRequiredKeyOf<NewTICK>;

type TICKFormDefaults = Pick<NewTICK, 'id'>;

type TICKFormGroupContent = {
  id: FormControl<ITICK['id'] | NewTICK['id']>;
  instrumentKey: FormControl<ITICK['instrumentKey']>;
  exchangeToken: FormControl<ITICK['exchangeToken']>;
  tradingSymbol: FormControl<ITICK['tradingSymbol']>;
  name: FormControl<ITICK['name']>;
  lastPrice: FormControl<ITICK['lastPrice']>;
  expiry: FormControl<ITICK['expiry']>;
  strike: FormControl<ITICK['strike']>;
  tickSize: FormControl<ITICK['tickSize']>;
  lotSize: FormControl<ITICK['lotSize']>;
  instrumentType: FormControl<ITICK['instrumentType']>;
  optionType: FormControl<ITICK['optionType']>;
  exchange: FormControl<ITICK['exchange']>;
};

export type TICKFormGroup = FormGroup<TICKFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TICKFormService {
  createTICKFormGroup(tICK: TICKFormGroupInput = { id: null }): TICKFormGroup {
    const tICKRawValue = {
      ...this.getFormDefaults(),
      ...tICK,
    };
    return new FormGroup<TICKFormGroupContent>({
      id: new FormControl(
        { value: tICKRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      instrumentKey: new FormControl(tICKRawValue.instrumentKey),
      exchangeToken: new FormControl(tICKRawValue.exchangeToken),
      tradingSymbol: new FormControl(tICKRawValue.tradingSymbol),
      name: new FormControl(tICKRawValue.name),
      lastPrice: new FormControl(tICKRawValue.lastPrice),
      expiry: new FormControl(tICKRawValue.expiry),
      strike: new FormControl(tICKRawValue.strike),
      tickSize: new FormControl(tICKRawValue.tickSize),
      lotSize: new FormControl(tICKRawValue.lotSize),
      instrumentType: new FormControl(tICKRawValue.instrumentType),
      optionType: new FormControl(tICKRawValue.optionType),
      exchange: new FormControl(tICKRawValue.exchange),
    });
  }

  getTICK(form: TICKFormGroup): ITICK | NewTICK {
    return form.getRawValue() as ITICK | NewTICK;
  }

  resetForm(form: TICKFormGroup, tICK: TICKFormGroupInput): void {
    const tICKRawValue = { ...this.getFormDefaults(), ...tICK };
    form.reset(
      {
        ...tICKRawValue,
        id: { value: tICKRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TICKFormDefaults {
    return {
      id: null,
    };
  }
}
