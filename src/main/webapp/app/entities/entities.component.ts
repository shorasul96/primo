import { defineComponent, provide } from 'vue';

import UserService from '@/entities/user/user.service';
import MarketingService from './marketing/marketing.service';
import ManufactureStageService from './manufacture-stage/manufacture-stage.service';
import UnitService from './unit/unit.service';
import InventoryService from './inventory/inventory.service';
import PartialObtainService from './partial-obtain/partial-obtain.service';
import ProductService from './product/product.service';
import CategoryService from './category/category.service';
import BalanceService from './balance/balance.service';
import TransactionHistoryService from './transaction-history/transaction-history.service';
import CurrencyService from './currency/currency.service';
import CustomerService from './customer/customer.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('marketingService', () => new MarketingService());
    provide('manufactureStageService', () => new ManufactureStageService());
    provide('unitService', () => new UnitService());
    provide('inventoryService', () => new InventoryService());
    provide('partialObtainService', () => new PartialObtainService());
    provide('productService', () => new ProductService());
    provide('categoryService', () => new CategoryService());
    provide('balanceService', () => new BalanceService());
    provide('transactionHistoryService', () => new TransactionHistoryService());
    provide('currencyService', () => new CurrencyService());
    provide('customerService', () => new CustomerService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
