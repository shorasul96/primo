import { defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { IPartialObtain } from '@/shared/model/partial-obtain.model';
import PartialObtainService from './partial-obtain.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PartialObtainDetails',
  setup() {
    const partialObtainService = inject('partialObtainService', () => new PartialObtainService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const partialObtain: Ref<IPartialObtain> = ref({});

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

    return {
      alertService,
      partialObtain,

      previousState,
      t$: useI18n().t,
    };
  },
});
