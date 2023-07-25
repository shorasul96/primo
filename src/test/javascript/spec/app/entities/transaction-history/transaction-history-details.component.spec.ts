/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import TransactionHistoryDetails from '../../../../../../main/webapp/app/entities/transaction-history/transaction-history-details.vue';
import TransactionHistoryService from '../../../../../../main/webapp/app/entities/transaction-history/transaction-history.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type TransactionHistoryDetailsComponentType = InstanceType<typeof TransactionHistoryDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const transactionHistorySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TransactionHistory Management Detail Component', () => {
    let transactionHistoryServiceStub: SinonStubbedInstance<TransactionHistoryService>;
    let mountOptions: MountingOptions<TransactionHistoryDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      transactionHistoryServiceStub = sinon.createStubInstance<TransactionHistoryService>(TransactionHistoryService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          transactionHistoryService: () => transactionHistoryServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        transactionHistoryServiceStub.find.resolves(transactionHistorySample);
        route = {
          params: {
            transactionHistoryId: '' + 123,
          },
        };
        const wrapper = shallowMount(TransactionHistoryDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.transactionHistory).toMatchObject(transactionHistorySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        transactionHistoryServiceStub.find.resolves(transactionHistorySample);
        const wrapper = shallowMount(TransactionHistoryDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
