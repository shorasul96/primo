import { IProduct } from '@/shared/model/product.model';

export interface ICategory {
  id?: number;
  name?: string | null;
  description?: string | null;
  categories?: ICategory[] | null;
  products?: IProduct[] | null;
  parent?: ICategory | null;
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public name?: string | null,
    public description?: string | null,
    public categories?: ICategory[] | null,
    public products?: IProduct[] | null,
    public parent?: ICategory | null
  ) {}
}
