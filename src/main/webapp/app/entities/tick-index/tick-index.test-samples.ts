import { ITickIndex, NewTickIndex } from './tick-index.model';

export const sampleWithRequiredData: ITickIndex = {
  id: 19286,
};

export const sampleWithPartialData: ITickIndex = {
  id: 23422,
  instrumentKey: 'disgrace continually',
  expiry: 'confusion commingle crossly',
  strike: 'voluntarily',
  tickSize: 'along harm pro',
  lotSize: 'a thrifty',
  optionType: 'tragic',
  exchange: 'athwart than towards',
};

export const sampleWithFullData: ITickIndex = {
  id: 1591,
  instrumentKey: 'why wealthy',
  exchangeToken: 'lest',
  tradingSymbol: 'regarding icky',
  name: 'austere past lurch',
  lastPrice: 'justly',
  expiry: 'unless subdued',
  strike: 'imaginary knowingly',
  tickSize: 'forenenst video',
  lotSize: 'excitedly whether highly',
  instrumentType: 'thoroughly',
  optionType: 'sock beyond failing',
  exchange: 'assess',
};

export const sampleWithNewData: NewTickIndex = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
