import { computed, defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { ICurrency, Currency } from '@/shared/model/currency.model';
import CurrencyService from './currency.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CurrencyUpdate',
  setup() {
    const currencyService = inject('currencyService', () => new CurrencyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const currency: Ref<ICurrency> = ref(new Currency());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {},
      rate: {},
      balance: {},
      transactionHistory: {},
    };
    const v$ = useVuelidate(validationRules, currency as any);
    v$.value.$validate();

    return {
      currencyService,
      alertService,
      currency,
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
      if (this.currency.id) {
        this.currencyService()
          .update(this.currency)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('storeApp.currency.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.currencyService()
          .create(this.currency)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('storeApp.currency.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
