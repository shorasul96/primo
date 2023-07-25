import { computed, defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ProductService from '@/entities/product/product.service';
import { IProduct } from '@/shared/model/product.model';
import UnitService from '@/entities/unit/unit.service';
import { IUnit } from '@/shared/model/unit.model';
import { IInventory, Inventory } from '@/shared/model/inventory.model';
import InventoryService from './inventory.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'InventoryUpdate',
  setup() {
    const inventoryService = inject('inventoryService', () => new InventoryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const inventory: Ref<IInventory> = ref(new Inventory());
    const productService = inject('productService', () => new ProductService());
    const products: Ref<IProduct[]> = ref([]);
    const unitService = inject('unitService', () => new UnitService());
    const units: Ref<IUnit[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveInventory = async inventoryId => {
      try {
        const res = await inventoryService().find(inventoryId);
        inventory.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.inventoryId) {
      retrieveInventory(route.params.inventoryId);
    }

    const initRelationships = () => {
      productService()
        .retrieve()
        .then(res => {
          products.value = res.data;
        });
      unitService()
        .retrieve()
        .then(res => {
          units.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      inStock: {},
      booked: {},
      claim: {},
      product: {},
      unit: {},
      partialObtains: {},
    };
    const v$ = useVuelidate(validationRules, inventory as any);
    v$.value.$validate();

    return {
      inventoryService,
      alertService,
      inventory,
      previousState,
      isSaving,
      currentLanguage,
      products,
      units,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.inventory.id) {
        this.inventoryService()
          .update(this.inventory)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('storeApp.inventory.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.inventoryService()
          .create(this.inventory)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('storeApp.inventory.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
