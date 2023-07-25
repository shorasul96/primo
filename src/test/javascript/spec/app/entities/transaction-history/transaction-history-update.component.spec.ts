/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import TransactionHistoryUpdate from '../../../../../../main/webapp/app/entities/transaction-history/transaction-history-update.vue';
import TransactionHistoryService from '../../../../../../main/webapp/app/entities/transaction-history/transaction-history.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

import CurrencyService from '../../../../../../main/webapp/app/entities/currency/currency.service';
import CustomerService from '../../../../../../main/webapp/app/entities/customer/customer.service';

type TransactionHistoryUpdateComponentType = InstanceType<typeof TransactionHistoryUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const transactionHistorySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TransactionHistoryUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TransactionHistory Management Update Component', () => {
    let comp: TransactionHistoryUpdateComponentType;
    let transactionHistoryServiceStub: SinonStubbedInstance<TransactionHistoryService>;

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
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          transactionHistoryService: () => transactionHistoryServiceStub,
          currencyService: () =>
            sinon.createStubInstance<CurrencyService>(CurrencyService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          customerService: () =>
            sinon.createStubInstance<CustomerService>(CustomerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TransactionHistoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.transactionHistory = transactionHistorySample;
        transactionHistoryServiceStub.update.resolves(transactionHistorySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(transactionHistoryServiceStub.update.calledWith(transactionHistorySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        transactionHistoryServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TransactionHistoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.transactionHistory = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(transactionHistoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        transactionHistoryServiceStub.find.resolves(transactionHistorySample);
        transactionHistoryServiceStub.retrieve.resolves([transactionHistorySample]);

        // WHEN
        route = {
          params: {
            transactionHistoryId: '' + transactionHistorySample.id,
          },
        };
        const wrapper = shallowMount(TransactionHistoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.transactionHistory).toMatchObject(transactionHistorySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        transactionHistoryServiceStub.find.resolves(transactionHistorySample);
        const wrapper = shallowMount(TransactionHistoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
