import { defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { ICustomer } from '@/shared/model/customer.model';
import CustomerService from './customer.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CustomerDetails',
  setup() {
    const customerService = inject('customerService', () => new CustomerService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const customer: Ref<ICustomer> = ref({});

    const retrieveCustomer = async customerId => {
      try {
        const res = await customerService().find(customerId);
        customer.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.customerId) {
      retrieveCustomer(route.params.customerId);
    }

    return {
      alertService,
      customer,

      previousState,
      t$: useI18n().t,
    };
  },
});
