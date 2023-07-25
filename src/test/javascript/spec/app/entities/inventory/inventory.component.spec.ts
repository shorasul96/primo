/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import Inventory from '../../../../../../main/webapp/app/entities/inventory/inventory.vue';
import InventoryService from '../../../../../../main/webapp/app/entities/inventory/inventory.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type InventoryComponentType = InstanceType<typeof Inventory>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Inventory Management Component', () => {
    let inventoryServiceStub: SinonStubbedInstance<InventoryService>;
    let mountOptions: MountingOptions<InventoryComponentType>['global'];

    beforeEach(() => {
      inventoryServiceStub = sinon.createStubInstance<InventoryService>(InventoryService);
      inventoryServiceStub.retrieve.resolves({ headers: {} });

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
          inventoryService: () => inventoryServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        inventoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Inventory, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(inventoryServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.inventories[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(Inventory, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(inventoryServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: InventoryComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Inventory, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        inventoryServiceStub.retrieve.reset();
        inventoryServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        inventoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(inventoryServiceStub.retrieve.called).toBeTruthy();
        expect(comp.inventories[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(inventoryServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        inventoryServiceStub.retrieve.reset();
        inventoryServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(inventoryServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.inventories[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(inventoryServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        inventoryServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeInventory();
        await comp.$nextTick(); // clear components

        // THEN
        expect(inventoryServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(inventoryServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
