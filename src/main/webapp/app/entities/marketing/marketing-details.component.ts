import { defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { IMarketing } from '@/shared/model/marketing.model';
import MarketingService from './marketing.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MarketingDetails',
  setup() {
    const marketingService = inject('marketingService', () => new MarketingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const marketing: Ref<IMarketing> = ref({});

    const retrieveMarketing = async marketingId => {
      try {
        const res = await marketingService().find(marketingId);
        marketing.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.marketingId) {
      retrieveMarketing(route.params.marketingId);
    }

    return {
      alertService,
      marketing,

      previousState,
      t$: useI18n().t,
    };
  },
});
