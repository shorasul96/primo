/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import PartialObtainUpdate from '../../../../../../main/webapp/app/entities/partial-obtain/partial-obtain-update.vue';
import PartialObtainService from '../../../../../../main/webapp/app/entities/partial-obtain/partial-obtain.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

import UnitService from '../../../../../../main/webapp/app/entities/unit/unit.service';
import CustomerService from '../../../../../../main/webapp/app/entities/customer/customer.service';
import InventoryService from '../../../../../../main/webapp/app/entities/inventory/inventory.service';

type PartialObtainUpdateComponentType = InstanceType<typeof PartialObtainUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const partialObtainSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PartialObtainUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('PartialObtain Management Update Component', () => {
    let comp: PartialObtainUpdateComponentType;
    let partialObtainServiceStub: SinonStubbedInstance<PartialObtainService>;

    beforeEach(() => {
      route = {};
      partialObtainServiceStub = sinon.createStubInstance<PartialObtainService>(PartialObtainService);

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
          partialObtainService: () => partialObtainServiceStub,
          unitService: () =>
            sinon.createStubInstance<UnitService>(UnitService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          customerService: () =>
            sinon.createStubInstance<CustomerService>(CustomerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          inventoryService: () =>
            sinon.createStubInstance<InventoryService>(InventoryService, {
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
        const wrapper = shallowMount(PartialObtainUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.partialObtain = partialObtainSample;
        partialObtainServiceStub.update.resolves(partialObtainSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(partialObtainServiceStub.update.calledWith(partialObtainSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        partialObtainServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PartialObtainUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.partialObtain = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(partialObtainServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        partialObtainServiceStub.find.resolves(partialObtainSample);
        partialObtainServiceStub.retrieve.resolves([partialObtainSample]);

        // WHEN
        route = {
          params: {
            partialObtainId: '' + partialObtainSample.id,
          },
        };
        const wrapper = shallowMount(PartialObtainUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.partialObtain).toMatchObject(partialObtainSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        partialObtainServiceStub.find.resolves(partialObtainSample);
        const wrapper = shallowMount(PartialObtainUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
