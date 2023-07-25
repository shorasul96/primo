import { computed, defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CurrencyService from '@/entities/currency/currency.service';
import { ICurrency } from '@/shared/model/currency.model';
import CustomerService from '@/entities/customer/customer.service';
import { ICustomer } from '@/shared/model/customer.model';
import { ITransactionHistory, TransactionHistory } from '@/shared/model/transaction-history.model';
import TransactionHistoryService from './transaction-history.service';
import { TransactionType } from '@/shared/model/enumerations/transaction-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TransactionHistoryUpdate',
  setup() {
    const transactionHistoryService = inject('transactionHistoryService', () => new TransactionHistoryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const transactionHistory: Ref<ITransactionHistory> = ref(new TransactionHistory());
    const currencyService = inject('currencyService', () => new CurrencyService());
    const currencies: Ref<ICurrency[]> = ref([]);
    const customerService = inject('customerService', () => new CustomerService());
    const customers: Ref<ICustomer[]> = ref([]);
    const transactionTypeValues: Ref<string[]> = ref(Object.keys(TransactionType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      currencyService()
        .retrieve()
        .then(res => {
          currencies.value = res.data;
        });
      customerService()
        .retrieve()
        .then(res => {
          customers.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      amount: {},
      description: {},
      type: {},
      currency: {},
      client: {},
    };
    const v$ = useVuelidate(validationRules, transactionHistory as any);
    v$.value.$validate();

    return {
      transactionHistoryService,
      alertService,
      transactionHistory,
      previousState,
      transactionTypeValues,
      isSaving,
      currentLanguage,
      currencies,
      customers,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.transactionHistory.id) {
        this.transactionHistoryService()
          .update(this.transactionHistory)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('storeApp.transactionHistory.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.transactionHistoryService()
          .create(this.transactionHistory)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('storeApp.transactionHistory.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
