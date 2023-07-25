/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import MarketingUpdate from '../../../../../../main/webapp/app/entities/marketing/marketing-update.vue';
import MarketingService from '../../../../../../main/webapp/app/entities/marketing/marketing.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

import CustomerService from '../../../../../../main/webapp/app/entities/customer/customer.service';
import ProductService from '../../../../../../main/webapp/app/entities/product/product.service';

type MarketingUpdateComponentType = InstanceType<typeof MarketingUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const marketingSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MarketingUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Marketing Management Update Component', () => {
    let comp: MarketingUpdateComponentType;
    let marketingServiceStub: SinonStubbedInstance<MarketingService>;

    beforeEach(() => {
      route = {};
      marketingServiceStub = sinon.createStubInstance<MarketingService>(MarketingService);

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
          marketingService: () => marketingServiceStub,
          customerService: () =>
            sinon.createStubInstance<CustomerService>(CustomerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          productService: () =>
            sinon.createStubInstance<ProductService>(ProductService, {
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
        const wrapper = shallowMount(MarketingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.marketing = marketingSample;
        marketingServiceStub.update.resolves(marketingSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(marketingServiceStub.update.calledWith(marketingSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        marketingServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MarketingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.marketing = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(marketingServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        marketingServiceStub.find.resolves(marketingSample);
        marketingServiceStub.retrieve.resolves([marketingSample]);

        // WHEN
        route = {
          params: {
            marketingId: '' + marketingSample.id,
          },
        };
        const wrapper = shallowMount(MarketingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.marketing).toMatchObject(marketingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        marketingServiceStub.find.resolves(marketingSample);
        const wrapper = shallowMount(MarketingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
