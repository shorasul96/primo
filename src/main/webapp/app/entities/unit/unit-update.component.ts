import { computed, defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { IUnit, Unit } from '@/shared/model/unit.model';
import UnitService from './unit.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'UnitUpdate',
  setup() {
    const unitService = inject('unitService', () => new UnitService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const unit: Ref<IUnit> = ref(new Unit());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveUnit = async unitId => {
      try {
        const res = await unitService().find(unitId);
        unit.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.unitId) {
      retrieveUnit(route.params.unitId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      measurement: {},
      description: {},
      inventory: {},
      partialObtain: {},
    };
    const v$ = useVuelidate(validationRules, unit as any);
    v$.value.$validate();

    return {
      unitService,
      alertService,
      unit,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.unit.id) {
        this.unitService()
          .update(this.unit)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('storeApp.unit.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.unitService()
          .create(this.unit)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('storeApp.unit.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
