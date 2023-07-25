import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Marketing = () => import('@/entities/marketing/marketing.vue');
const MarketingUpdate = () => import('@/entities/marketing/marketing-update.vue');
const MarketingDetails = () => import('@/entities/marketing/marketing-details.vue');

const ManufactureStage = () => import('@/entities/manufacture-stage/manufacture-stage.vue');
const ManufactureStageUpdate = () => import('@/entities/manufacture-stage/manufacture-stage-update.vue');
const ManufactureStageDetails = () => import('@/entities/manufacture-stage/manufacture-stage-details.vue');

const Unit = () => import('@/entities/unit/unit.vue');
const UnitUpdate = () => import('@/entities/unit/unit-update.vue');
const UnitDetails = () => import('@/entities/unit/unit-details.vue');

const Inventory = () => import('@/entities/inventory/inventory.vue');
const InventoryUpdate = () => import('@/entities/inventory/inventory-update.vue');
const InventoryDetails = () => import('@/entities/inventory/inventory-details.vue');

const PartialObtain = () => import('@/entities/partial-obtain/partial-obtain.vue');
const PartialObtainUpdate = () => import('@/entities/partial-obtain/partial-obtain-update.vue');
const PartialObtainDetails = () => import('@/entities/partial-obtain/partial-obtain-details.vue');

const Product = () => import('@/entities/product/product.vue');
const ProductUpdate = () => import('@/entities/product/product-update.vue');
const ProductDetails = () => import('@/entities/product/product-details.vue');

const Category = () => import('@/entities/category/category.vue');
const CategoryUpdate = () => import('@/entities/category/category-update.vue');
const CategoryDetails = () => import('@/entities/category/category-details.vue');

const Balance = () => import('@/entities/balance/balance.vue');
const BalanceUpdate = () => import('@/entities/balance/balance-update.vue');
const BalanceDetails = () => import('@/entities/balance/balance-details.vue');

const TransactionHistory = () => import('@/entities/transaction-history/transaction-history.vue');
const TransactionHistoryUpdate = () => import('@/entities/transaction-history/transaction-history-update.vue');
const TransactionHistoryDetails = () => import('@/entities/transaction-history/transaction-history-details.vue');

const Currency = () => import('@/entities/currency/currency.vue');
const CurrencyUpdate = () => import('@/entities/currency/currency-update.vue');
const CurrencyDetails = () => import('@/entities/currency/currency-details.vue');

const Customer = () => import('@/entities/customer/customer.vue');
const CustomerUpdate = () => import('@/entities/customer/customer-update.vue');
const CustomerDetails = () => import('@/entities/customer/customer-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'marketing',
      name: 'Marketing',
      component: Marketing,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'marketing/new',
      name: 'MarketingCreate',
      component: MarketingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'marketing/:marketingId/edit',
      name: 'MarketingEdit',
      component: MarketingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'marketing/:marketingId/view',
      name: 'MarketingView',
      component: MarketingDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'manufacture-stage',
      name: 'ManufactureStage',
      component: ManufactureStage,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'manufacture-stage/new',
      name: 'ManufactureStageCreate',
      component: ManufactureStageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'manufacture-stage/:manufactureStageId/edit',
      name: 'ManufactureStageEdit',
      component: ManufactureStageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'manufacture-stage/:manufactureStageId/view',
      name: 'ManufactureStageView',
      component: ManufactureStageDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unit',
      name: 'Unit',
      component: Unit,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unit/new',
      name: 'UnitCreate',
      component: UnitUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unit/:unitId/edit',
      name: 'UnitEdit',
      component: UnitUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'unit/:unitId/view',
      name: 'UnitView',
      component: UnitDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventory',
      name: 'Inventory',
      component: Inventory,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventory/new',
      name: 'InventoryCreate',
      component: InventoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventory/:inventoryId/edit',
      name: 'InventoryEdit',
      component: InventoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'inventory/:inventoryId/view',
      name: 'InventoryView',
      component: InventoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'partial-obtain',
      name: 'PartialObtain',
      component: PartialObtain,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'partial-obtain/new',
      name: 'PartialObtainCreate',
      component: PartialObtainUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'partial-obtain/:partialObtainId/edit',
      name: 'PartialObtainEdit',
      component: PartialObtainUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'partial-obtain/:partialObtainId/view',
      name: 'PartialObtainView',
      component: PartialObtainDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product',
      name: 'Product',
      component: Product,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/new',
      name: 'ProductCreate',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/edit',
      name: 'ProductEdit',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/view',
      name: 'ProductView',
      component: ProductDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category',
      name: 'Category',
      component: Category,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/new',
      name: 'CategoryCreate',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/edit',
      name: 'CategoryEdit',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/view',
      name: 'CategoryView',
      component: CategoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'balance',
      name: 'Balance',
      component: Balance,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'balance/new',
      name: 'BalanceCreate',
      component: BalanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'balance/:balanceId/edit',
      name: 'BalanceEdit',
      component: BalanceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'balance/:balanceId/view',
      name: 'BalanceView',
      component: BalanceDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transaction-history',
      name: 'TransactionHistory',
      component: TransactionHistory,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transaction-history/new',
      name: 'TransactionHistoryCreate',
      component: TransactionHistoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transaction-history/:transactionHistoryId/edit',
      name: 'TransactionHistoryEdit',
      component: TransactionHistoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'transaction-history/:transactionHistoryId/view',
      name: 'TransactionHistoryView',
      component: TransactionHistoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'currency',
      name: 'Currency',
      component: Currency,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'currency/new',
      name: 'CurrencyCreate',
      component: CurrencyUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'currency/:currencyId/edit',
      name: 'CurrencyEdit',
      component: CurrencyUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'currency/:currencyId/view',
      name: 'CurrencyView',
      component: CurrencyDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer',
      name: 'Customer',
      component: Customer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/new',
      name: 'CustomerCreate',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/edit',
      name: 'CustomerEdit',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/view',
      name: 'CustomerView',
      component: CustomerDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
