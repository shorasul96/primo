import { computed, defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { IManufactureStage, ManufactureStage } from '@/shared/model/manufacture-stage.model';
import ManufactureStageService from './manufacture-stage.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ManufactureStageUpdate',
  setup() {
    const manufactureStageService = inject('manufactureStageService', () => new ManufactureStageService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const manufactureStage: Ref<IManufactureStage> = ref(new ManufactureStage());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveManufactureStage = async manufactureStageId => {
      try {
        const res = await manufactureStageService().find(manufactureStageId);
        manufactureStage.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.manufactureStageId) {
      retrieveManufactureStage(route.params.manufactureStageId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {},
      description: {},
      product: {},
    };
    const v$ = useVuelidate(validationRules, manufactureStage as any);
    v$.value.$validate();

    return {
      manufactureStageService,
      alertService,
      manufactureStage,
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
      if (this.manufactureStage.id) {
        this.manufactureStageService()
          .update(this.manufactureStage)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('storeApp.manufactureStage.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.manufactureStageService()
          .create(this.manufactureStage)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('storeApp.manufactureStage.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
