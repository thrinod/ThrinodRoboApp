import { ITickStock, NewTickStock } from './tick-stock.model';

export const sampleWithRequiredData: ITickStock = {
  id: 30558,
};

export const sampleWithPartialData: ITickStock = {
  id: 29687,
  exchangeToken: 'vice canine',
  tradingSymbol: 'fortunately',
  lastPrice: 'in',
  strike: 'cytoplasm overindulge concerned',
  lotSize: 'sans',
  instrumentType: 'pace lightly',
  optionType: 'tepee near',
};

export const sampleWithFullData: ITickStock = {
  id: 24466,
  instrumentKey: 'along extroverted',
  exchangeToken: 'regurgitate',
  tradingSymbol: 'irritating uh-huh beneath',
  name: 'epoch promotion heating',
  lastPrice: 'too pfft whoever',
  expiry: 'ugh codify',
  strike: 'if',
  tickSize: 'exculpate',
  lotSize: 'fooey expense precede',
  instrumentType: 'alfalfa',
  optionType: 'hence existence',
  exchange: 'apply or',
};

export const sampleWithNewData: NewTickStock = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
