import { defineComponent, inject, onMounted, ref, Ref, watch, watchEffect } from 'vue';
import { useI18n } from 'vue-i18n';

import { IPartialObtain } from '@/shared/model/partial-obtain.model';
import PartialObtainService from './partial-obtain.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PartialObtain',
  setup() {
    const { t: t$ } = useI18n();
    const partialObtainService = inject('partialObtainService', () => new PartialObtainService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const itemsPerPage = ref(20);
    const queryCount: Ref<number> = ref(null);
    const page: Ref<number> = ref(1);
    const propOrder = ref('id');
    const reverse = ref(false);
    const totalItems = ref(0);

    const partialObtains: Ref<IPartialObtain[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {
      page.value = 1;
    };

    const sort = (): Array<any> => {
      const result = [propOrder.value + ',' + (reverse.value ? 'desc' : 'asc')];
      if (propOrder.value !== 'id') {
        result.push('id');
      }
      return result;
    };

    const retrievePartialObtains = async () => {
      isFetching.value = true;
      try {
        const paginationQuery = {
          page: page.value - 1,
          size: itemsPerPage.value,
          sort: sort(),
        };
        const res = await partialObtainService().retrieve(paginationQuery);
        totalItems.value = Number(res.headers['x-total-count']);
        queryCount.value = totalItems.value;
        partialObtains.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePartialObtains();
    };

    onMounted(async () => {
      await retrievePartialObtains();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPartialObtain) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePartialObtain = async () => {
      try {
        await partialObtainService().delete(removeId.value);
        const message = t$('storeApp.partialObtain.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePartialObtains();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    const changeOrder = (newOrder: string) => {
      if (propOrder.value === newOrder) {
        reverse.value = !reverse.value;
      } else {
        reverse.value = false;
      }
      propOrder.value = newOrder;
    };

    // Whenever order changes, reset the pagination
    watch([propOrder, reverse], async () => {
      if (page.value === 1) {
        // first page, retrieve new data
        await retrievePartialObtains();
      } else {
        // reset the pagination
        clear();
      }
    });

    // Whenever page changes, switch to the new page.
    watch(page, async () => {
      await retrievePartialObtains();
    });

    return {
      partialObtains,
      handleSyncList,
      isFetching,
      retrievePartialObtains,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePartialObtain,
      itemsPerPage,
      queryCount,
      page,
      propOrder,
      reverse,
      totalItems,
      changeOrder,
      t$,
    };
  },
});
