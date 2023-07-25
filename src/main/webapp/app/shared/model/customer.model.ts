import { ITransactionHistory } from '@/shared/model/transaction-history.model';
import { IMarketing } from '@/shared/model/marketing.model';
import { IPartialObtain } from '@/shared/model/partial-obtain.model';

import { CustomerType } from '@/shared/model/enumerations/customer-type.model';
export interface ICustomer {
  id?: number;
  fullName?: string | null;
  companyName?: string | null;
  customerType?: keyof typeof CustomerType | null;
  transactionHistories?: ITransactionHistory[] | null;
  marketings?: IMarketing[] | null;
  partialObtains?: IPartialObtain[] | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public fullName?: string | null,
    public companyName?: string | null,
    public customerType?: keyof typeof CustomerType | null,
    public transactionHistories?: ITransactionHistory[] | null,
    public marketings?: IMarketing[] | null,
    public partialObtains?: IPartialObtain[] | null
  ) {}
}
