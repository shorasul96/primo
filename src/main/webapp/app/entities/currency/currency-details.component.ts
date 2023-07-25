import { defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { ICurrency } from '@/shared/model/currency.model';
import CurrencyService from './currency.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CurrencyDetails',
  setup() {
    const currencyService = inject('currencyService', () => new CurrencyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const currency: Ref<ICurrency> = ref({});

    const retrieveCurrency = async currencyId => {
      try {
        const res = await currencyService().find(currencyId);
        currency.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.currencyId) {
      retrieveCurrency(route.params.currencyId);
    }

    return {
      alertService,
      currency,

      previousState,
      t$: useI18n().t,
    };
  },
});
