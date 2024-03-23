import { ITickFutures, NewTickFutures } from './tick-futures.model';

export const sampleWithRequiredData: ITickFutures = {
  id: 32148,
};

export const sampleWithPartialData: ITickFutures = {
  id: 22485,
  instrumentKey: 'enormous modulo',
  name: 'oh',
  expiry: 'yet puggle',
  strike: 'likable who',
  tickSize: 'memorable boo',
  lotSize: 'before',
  instrumentType: 'poke but boohoo',
  optionType: 'yum',
};

export const sampleWithFullData: ITickFutures = {
  id: 14577,
  instrumentKey: 'example',
  exchangeToken: 'pace accidentally simmer',
  tradingSymbol: 'than root',
  name: 'ripen psst',
  lastPrice: 'beak per',
  expiry: 'illustrate',
  strike: 'loose incidentally',
  tickSize: 'emergence',
  lotSize: 'for noisily',
  instrumentType: 'ack dupe daub',
  optionType: 'minus kindly consequently',
  exchange: 'retool',
};

export const sampleWithNewData: NewTickFutures = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
