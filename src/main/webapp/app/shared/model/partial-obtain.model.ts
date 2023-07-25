import { IUnit } from '@/shared/model/unit.model';
import { ICustomer } from '@/shared/model/customer.model';
import { IInventory } from '@/shared/model/inventory.model';

export interface IPartialObtain {
  id?: number;
  unit?: IUnit | null;
  customer?: ICustomer | null;
  item?: IInventory | null;
}

export class PartialObtain implements IPartialObtain {
  constructor(public id?: number, public unit?: IUnit | null, public customer?: ICustomer | null, public item?: IInventory | null) {}
}
