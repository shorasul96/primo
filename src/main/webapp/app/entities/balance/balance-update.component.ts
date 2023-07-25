import { computed, defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CurrencyService from '@/entities/currency/currency.service';
import { ICurrency } from '@/shared/model/currency.model';
import { IBalance, Balance } from '@/shared/model/balance.model';
import BalanceService from './balance.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'BalanceUpdate',
  setup() {
    const balanceService = inject('balanceService', () => new BalanceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const balance: Ref<IBalance> = ref(new Balance());
    const currencyService = inject('currencyService', () => new CurrencyService());
    const currencies: Ref<ICurrency[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      currencyService()
        .retrieve()
        .then(res => {
          currencies.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      amount: {},
      loan: {},
      currency: {},
    };
    const v$ = useVuelidate(validationRules, balance as any);
    v$.value.$validate();

    return {
      balanceService,
      alertService,
      balance,
      previousState,
      isSaving,
      currentLanguage,
      currencies,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.balance.id) {
        this.balanceService()
          .update(this.balance)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('storeApp.balance.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.balanceService()
          .create(this.balance)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('storeApp.balance.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
