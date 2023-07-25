import { ICurrency } from '@/shared/model/currency.model';

export interface IBalance {
  id?: number;
  amount?: number | null;
  loan?: number | null;
  currency?: ICurrency | null;
}

export class Balance implements IBalance {
  constructor(public id?: number, public amount?: number | null, public loan?: number | null, public currency?: ICurrency | null) {}
}
