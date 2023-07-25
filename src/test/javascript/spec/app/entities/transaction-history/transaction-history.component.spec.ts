/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import TransactionHistory from '../../../../../../main/webapp/app/entities/transaction-history/transaction-history.vue';
import TransactionHistoryService from '../../../../../../main/webapp/app/entities/transaction-history/transaction-history.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type TransactionHistoryComponentType = InstanceType<typeof TransactionHistory>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('TransactionHistory Management Component', () => {
    let transactionHistoryServiceStub: SinonStubbedInstance<TransactionHistoryService>;
    let mountOptions: MountingOptions<TransactionHistoryComponentType>['global'];

    beforeEach(() => {
      transactionHistoryServiceStub = sinon.createStubInstance<TransactionHistoryService>(TransactionHistoryService);
      transactionHistoryServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          transactionHistoryService: () => transactionHistoryServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        transactionHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(TransactionHistory, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(transactionHistoryServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.transactionHistories[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(TransactionHistory, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(transactionHistoryServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: TransactionHistoryComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(TransactionHistory, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        transactionHistoryServiceStub.retrieve.reset();
        transactionHistoryServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        transactionHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(transactionHistoryServiceStub.retrieve.called).toBeTruthy();
        expect(comp.transactionHistories[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(transactionHistoryServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        transactionHistoryServiceStub.retrieve.reset();
        transactionHistoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(transactionHistoryServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.transactionHistories[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(transactionHistoryServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        transactionHistoryServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeTransactionHistory();
        await comp.$nextTick(); // clear components

        // THEN
        expect(transactionHistoryServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(transactionHistoryServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
