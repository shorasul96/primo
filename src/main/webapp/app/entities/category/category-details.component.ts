import { defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import { ICategory } from '@/shared/model/category.model';
import CategoryService from './category.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CategoryDetails',
  setup() {
    const categoryService = inject('categoryService', () => new CategoryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const category: Ref<ICategory> = ref({});

    const retrieveCategory = async categoryId => {
      try {
        const res = await categoryService().find(categoryId);
        category.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.categoryId) {
      retrieveCategory(route.params.categoryId);
    }

    return {
      alertService,
      category,

      previousState,
      t$: useI18n().t,
    };
  },
});
