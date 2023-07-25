import { computed, defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CustomerService from '@/entities/customer/customer.service';
import { ICustomer } from '@/shared/model/customer.model';
import ProductService from '@/entities/product/product.service';
import { IProduct } from '@/shared/model/product.model';
import { IMarketing, Marketing } from '@/shared/model/marketing.model';
import MarketingService from './marketing.service';
import { DealType } from '@/shared/model/enumerations/deal-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MarketingUpdate',
  setup() {
    const marketingService = inject('marketingService', () => new MarketingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const marketing: Ref<IMarketing> = ref(new Marketing());
    const customerService = inject('customerService', () => new CustomerService());
    const customers: Ref<ICustomer[]> = ref([]);
    const productService = inject('productService', () => new ProductService());
    const products: Ref<IProduct[]> = ref([]);
    const dealTypeValues: Ref<string[]> = ref(Object.keys(DealType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      customerService()
        .retrieve()
        .then(res => {
          customers.value = res.data;
        });
      productService()
        .retrieve()
        .then(res => {
          products.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      deal: {},
      customer: {},
      product: {},
    };
    const v$ = useVuelidate(validationRules, marketing as any);
    v$.value.$validate();

    return {
      marketingService,
      alertService,
      marketing,
      previousState,
      dealTypeValues,
      isSaving,
      currentLanguage,
      customers,
      products,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.marketing.id) {
        this.marketingService()
          .update(this.marketing)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('storeApp.marketing.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.marketingService()
          .create(this.marketing)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('storeApp.marketing.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
