import { IManufactureStage } from '@/shared/model/manufacture-stage.model';
import { IMarketing } from '@/shared/model/marketing.model';
import { ICategory } from '@/shared/model/category.model';
import { IInventory } from '@/shared/model/inventory.model';

export interface IProduct {
  id?: number;
  name?: string | null;
  description?: string | null;
  stage?: IManufactureStage | null;
  marketings?: IMarketing[] | null;
  category?: ICategory | null;
  inventory?: IInventory | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string | null,
    public description?: string | null,
    public stage?: IManufactureStage | null,
    public marketings?: IMarketing[] | null,
    public category?: ICategory | null,
    public inventory?: IInventory | null
  ) {}
}
