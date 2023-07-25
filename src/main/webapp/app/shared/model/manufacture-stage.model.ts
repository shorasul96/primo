import { IProduct } from '@/shared/model/product.model';

export interface IManufactureStage {
  id?: number;
  name?: string | null;
  description?: string | null;
  product?: IProduct | null;
}

export class ManufactureStage implements IManufactureStage {
  constructor(public id?: number, public name?: string | null, public description?: string | null, public product?: IProduct | null) {}
}
