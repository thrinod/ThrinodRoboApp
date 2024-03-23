import { ITICK, NewTICK } from './tick.model';

export const sampleWithRequiredData: ITICK = {
  id: 30802,
};

export const sampleWithPartialData: ITICK = {
  id: 2162,
  tradingSymbol: 'except amongst geez',
  expiry: 'above pro',
  tickSize: 'now how fully',
  instrumentType: 'silver',
  exchange: 'valuable onto',
};

export const sampleWithFullData: ITICK = {
  id: 12256,
  instrumentKey: 'phew',
  exchangeToken: 'banner submissive',
  tradingSymbol: 'because',
  name: 'readily miserably admired',
  lastPrice: 'slushy aftermath closely',
  expiry: 'naturalisation yahoo instead',
  strike: 'yahoo spice',
  tickSize: 'provided shyly hm',
  lotSize: 'hidden phooey gah',
  instrumentType: 'onto or',
  optionType: 'outline',
  exchange: 'that motor lasting',
};

export const sampleWithNewData: NewTICK = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
