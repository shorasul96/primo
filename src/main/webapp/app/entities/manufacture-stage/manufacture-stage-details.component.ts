import { defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { IManufactureStage } from '@/shared/model/manufacture-stage.model';
import ManufactureStageService from './manufacture-stage.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ManufactureStageDetails',
  setup() {
    const manufactureStageService = inject('manufactureStageService', () => new ManufactureStageService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const manufactureStage: Ref<IManufactureStage> = ref({});

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

    return {
      alertService,
      manufactureStage,

      previousState,
      t$: useI18n().t,
    };
  },
});
