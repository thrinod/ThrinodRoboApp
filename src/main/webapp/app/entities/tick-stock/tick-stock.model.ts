export interface ITickStock {
  id: number;
  instrumentKey?: string | null;
  exchangeToken?: string | null;
  tradingSymbol?: string | null;
  name?: string | null;
  lastPrice?: string | null;
  expiry?: string | null;
  strike?: string | null;
  tickSize?: string | null;
  lotSize?: string | null;
  instrumentType?: string | null;
  optionType?: string | null;
  exchange?: string | null;
}

export type NewTickStock = Omit<ITickStock, 'id'> & { id: null };
