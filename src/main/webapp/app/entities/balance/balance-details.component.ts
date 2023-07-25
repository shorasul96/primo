import { defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { IBalance } from '@/shared/model/balance.model';
import BalanceService from './balance.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'BalanceDetails',
  setup() {
    const balanceService = inject('balanceService', () => new BalanceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const balance: Ref<IBalance> = ref({});

    const retrieveBalance = async balanceId => {
      try {
        const res = await balanceService().find(balanceId);
        balance.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.balanceId) {
      retrieveBalance(route.params.balanceId);
    }

    return {
      alertService,
      balance,

      previousState,
      t$: useI18n().t,
    };
  },
});
