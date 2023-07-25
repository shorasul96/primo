import { computed, defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import UnitService from '@/entities/unit/unit.service';
import { IUnit } from '@/shared/model/unit.model';
import CustomerService from '@/entities/customer/customer.service';
import { ICustomer } from '@/shared/model/customer.model';
import InventoryService from '@/entities/inventory/inventory.service';
import { IInventory } from '@/shared/model/inventory.model';
import { IPartialObtain, PartialObtain } from '@/shared/model/partial-obtain.model';
import PartialObtainService from './partial-obtain.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PartialObtainUpdate',
  setup() {
    const partialObtainService = inject('partialObtainService', () => new PartialObtainService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const partialObtain: Ref<IPartialObtain> = ref(new PartialObtain());
    const unitService = inject('unitService', () => new UnitService());
    const units: Ref<IUnit[]> = ref([]);
    const customerService = inject('customerService', () => new CustomerService());
    const customers: Ref<ICustomer[]> = ref([]);
    const inventoryService = inject('inventoryService', () => new InventoryService());
    const inventories: Ref<IInventory[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePartialObtain = async partialObtainId => {
      try {
        const res = await partialObtainService().find(partialObtainId);
        partialObtain.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.partialObtainId) {
      retrievePartialObtain(route.params.partialObtainId);
    }

    const initRelationships = () => {
      unitService()
        .retrieve()
        .then(res => {
          units.value = res.data;
        });
      customerService()
        .retrieve()
        .then(res => {
          customers.value = res.data;
        });
      inventoryService()
        .retrieve()
        .then(res => {
          inventories.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      unit: {},
      customer: {},
      item: {},
    };
    const v$ = useVuelidate(validationRules, partialObtain as any);
    v$.value.$validate();

    return {
      partialObtainService,
      alertService,
      partialObtain,
      previousState,
      isSaving,
      currentLanguage,
      units,
      customers,
      inventories,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.partialObtain.id) {
        this.partialObtainService()
          .update(this.partialObtain)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('storeApp.partialObtain.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.partialObtainService()
          .create(this.partialObtain)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('storeApp.partialObtain.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
