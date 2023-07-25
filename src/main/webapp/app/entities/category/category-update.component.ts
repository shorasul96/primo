import { computed, defineComponent, inject, ref, Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import CategoryService from '@/entities/category/category.service';
import { ICategory } from '@/shared/model/category.model';
import { ICategory, Category } from '@/shared/model/category.model';
import CategoryService from './category.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CategoryUpdate',
  setup() {
    const categoryService = inject('categoryService', () => new CategoryService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const category: Ref<ICategory> = ref(new Category());
    const categoryService = inject('categoryService', () => new CategoryService());
    const categories: Ref<ICategory[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      categoryService()
        .retrieve()
        .then(res => {
          categories.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {},
      description: {},
      categories: {},
      products: {},
      parent: {},
    };
    const v$ = useVuelidate(validationRules, category as any);
    v$.value.$validate();

    return {
      categoryService,
      alertService,
      category,
      previousState,
      isSaving,
      currentLanguage,
      categories,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.category.id) {
        this.categoryService()
          .update(this.category)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('storeApp.category.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.categoryService()
          .create(this.category)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('storeApp.category.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
