import { ICurrency } from '@/shared/model/currency.model';
import { ICustomer } from '@/shared/model/customer.model';

import { TransactionType } from '@/shared/model/enumerations/transaction-type.model';
export interface ITransactionHistory {
  id?: number;
  amount?: number | null;
  description?: string | null;
  type?: keyof typeof TransactionType | null;
  currency?: ICurrency | null;
  client?: ICustomer | null;
}

export class TransactionHistory implements ITransactionHistory {
  constructor(
    public id?: number,
    public amount?: number | null,
    public description?: string | null,
    public type?: keyof typeof TransactionType | null,
    public currency?: ICurrency | null,
    public client?: ICustomer | null
  ) {}
}
