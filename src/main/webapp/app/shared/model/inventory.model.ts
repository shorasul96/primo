import { IProduct } from '@/shared/model/product.model';
import { IUnit } from '@/shared/model/unit.model';
import { IPartialObtain } from '@/shared/model/partial-obtain.model';

export interface IInventory {
  id?: number;
  inStock?: number | null;
  booked?: number | null;
  claim?: number | null;
  product?: IProduct | null;
  unit?: IUnit | null;
  partialObtains?: IPartialObtain[] | null;
}

export class Inventory implements IInventory {
  constructor(
    public id?: number,
    public inStock?: number | null,
    public booked?: number | null,
    public claim?: number | null,
    public product?: IProduct | null,
    public unit?: IUnit | null,
    public partialObtains?: IPartialObtain[] | null
  ) {}
}
