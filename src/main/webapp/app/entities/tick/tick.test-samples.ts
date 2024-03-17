import { ITICK, NewTICK } from './tick.model';

export const sampleWithRequiredData: ITICK = {
  id: 12452,
};

export const sampleWithPartialData: ITICK = {
  id: 2445,
  instrumentKey: 'late lover so',
  lastPrice: 'till',
  instrumentType: 'why properly',
  exchange: 'bleak derivative',
};

export const sampleWithFullData: ITICK = {
  id: 11833,
  instrumentKey: 'methodology regarding or',
  exchangeToken: 'friendly submissive',
  tradingSymbol: 'officially hierarchy ouch',
  name: 'corrupt beautifully',
  lastPrice: 'resource irritably',
  expiry: 'delegate kindheartedly',
  strike: 'miserably transgress cat',
  tickSize: 'whose',
  lotSize: 'ew finally spring',
  instrumentType: 'wobbly',
  optionType: 'content as',
  exchange: 'boohoo including',
};

export const sampleWithNewData: NewTICK = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
