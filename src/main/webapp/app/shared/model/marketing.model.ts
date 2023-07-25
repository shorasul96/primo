import { ICustomer } from '@/shared/model/customer.model';
import { IProduct } from '@/shared/model/product.model';

import { DealType } from '@/shared/model/enumerations/deal-type.model';
export interface IMarketing {
  id?: number;
  deal?: keyof typeof DealType | null;
  customer?: ICustomer | null;
  product?: IProduct | null;
}

export class Marketing implements IMarketing {
  constructor(
    public id?: number,
    public deal?: keyof typeof DealType | null,
    public customer?: ICustomer | null,
    public product?: IProduct | null
  ) {}
}
