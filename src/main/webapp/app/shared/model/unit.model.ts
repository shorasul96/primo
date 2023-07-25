import { IInventory } from '@/shared/model/inventory.model';
import { IPartialObtain } from '@/shared/model/partial-obtain.model';

export interface IUnit {
  id?: number;
  measurement?: string | null;
  description?: string | null;
  inventory?: IInventory | null;
  partialObtain?: IPartialObtain | null;
}

export class Unit implements IUnit {
  constructor(
    public id?: number,
    public measurement?: string | null,
    public description?: string | null,
    public inventory?: IInventory | null,
    public partialObtain?: IPartialObtain | null
  ) {}
}
