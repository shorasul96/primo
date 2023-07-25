import { IBalance } from '@/shared/model/balance.model';
import { ITransactionHistory } from '@/shared/model/transaction-history.model';

export interface ICurrency {
  id?: number;
  name?: string | null;
  rate?: number | null;
  balance?: IBalance | null;
  transactionHistory?: ITransactionHistory | null;
}

export class Currency implements ICurrency {
  constructor(
    public id?: number,
    public name?: string | null,
    public rate?: number | null,
    public balance?: IBalance | null,
    public transactionHistory?: ITransactionHistory | null
  ) {}
}
