import { defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { IInventory } from '@/shared/model/inventory.model';
import InventoryService from './inventory.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'InventoryDetails',
  setup() {
    const inventoryService = inject('inventoryService', () => new InventoryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const inventory: Ref<IInventory> = ref({});

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

    return {
      alertService,
      inventory,

      previousState,
      t$: useI18n().t,
    };
  },
});
