import { defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { ITransactionHistory } from '@/shared/model/transaction-history.model';
import TransactionHistoryService from './transaction-history.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TransactionHistoryDetails',
  setup() {
    const transactionHistoryService = inject('transactionHistoryService', () => new TransactionHistoryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const transactionHistory: Ref<ITransactionHistory> = ref({});

    const retrieveTransactionHistory = async transactionHistoryId => {
      try {
        const res = await transactionHistoryService().find(transactionHistoryId);
        transactionHistory.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.transactionHistoryId) {
      retrieveTransactionHistory(route.params.transactionHistoryId);
    }

    return {
      alertService,
      transactionHistory,

      previousState,
      t$: useI18n().t,
    };
  },
});
