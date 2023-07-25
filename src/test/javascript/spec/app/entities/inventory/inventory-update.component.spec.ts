/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import InventoryUpdate from '../../../../../../main/webapp/app/entities/inventory/inventory-update.vue';
import InventoryService from '../../../../../../main/webapp/app/entities/inventory/inventory.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

import ProductService from '../../../../../../main/webapp/app/entities/product/product.service';
import UnitService from '../../../../../../main/webapp/app/entities/unit/unit.service';

type InventoryUpdateComponentType = InstanceType<typeof InventoryUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const inventorySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<InventoryUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Inventory Management Update Component', () => {
    let comp: InventoryUpdateComponentType;
    let inventoryServiceStub: SinonStubbedInstance<InventoryService>;

    beforeEach(() => {
      route = {};
      inventoryServiceStub = sinon.createStubInstance<InventoryService>(InventoryService);

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
          inventoryService: () => inventoryServiceStub,
          productService: () =>
            sinon.createStubInstance<ProductService>(ProductService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          unitService: () =>
            sinon.createStubInstance<UnitService>(UnitService, {
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
        const wrapper = shallowMount(InventoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.inventory = inventorySample;
        inventoryServiceStub.update.resolves(inventorySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(inventoryServiceStub.update.calledWith(inventorySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        inventoryServiceStub.create.resolves(entity);
        const wrapper = shallowMount(InventoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.inventory = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(inventoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        inventoryServiceStub.find.resolves(inventorySample);
        inventoryServiceStub.retrieve.resolves([inventorySample]);

        // WHEN
        route = {
          params: {
            inventoryId: '' + inventorySample.id,
          },
        };
        const wrapper = shallowMount(InventoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.inventory).toMatchObject(inventorySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        inventoryServiceStub.find.resolves(inventorySample);
        const wrapper = shallowMount(InventoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
