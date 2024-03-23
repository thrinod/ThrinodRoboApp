import { ITickOption, NewTickOption } from './tick-option.model';

export const sampleWithRequiredData: ITickOption = {
  id: 31451,
};

export const sampleWithPartialData: ITickOption = {
  id: 28757,
  name: 'palpitate knife circa',
  lastPrice: 'sulks',
  expiry: 'extra-small romaine',
};

export const sampleWithFullData: ITickOption = {
  id: 11126,
  instrumentKey: 'restructure upbeat',
  exchangeToken: 'yippee',
  tradingSymbol: 'however biplane',
  name: 'tabulate',
  lastPrice: 'petticoat yum',
  expiry: 'redevelop',
  strike: 'provided internal nocturnal',
  tickSize: 'but',
  lotSize: 'uncover anti blank',
  instrumentType: 'tight twilight wherever',
  optionType: 'ha',
  exchange: 'mediate',
};

export const sampleWithNewData: NewTickOption = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
