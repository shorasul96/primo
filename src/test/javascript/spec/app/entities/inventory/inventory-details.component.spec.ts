/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import InventoryDetails from '../../../../../../main/webapp/app/entities/inventory/inventory-details.vue';
import InventoryService from '../../../../../../main/webapp/app/entities/inventory/inventory.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type InventoryDetailsComponentType = InstanceType<typeof InventoryDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const inventorySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Inventory Management Detail Component', () => {
    let inventoryServiceStub: SinonStubbedInstance<InventoryService>;
    let mountOptions: MountingOptions<InventoryDetailsComponentType>['global'];

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
          'router-link': true,
        },
        provide: {
          alertService,
          inventoryService: () => inventoryServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        inventoryServiceStub.find.resolves(inventorySample);
        route = {
          params: {
            inventoryId: '' + 123,
          },
        };
        const wrapper = shallowMount(InventoryDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.inventory).toMatchObject(inventorySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        inventoryServiceStub.find.resolves(inventorySample);
        const wrapper = shallowMount(InventoryDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
