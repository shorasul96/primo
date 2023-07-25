/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import MarketingDetails from '../../../../../../main/webapp/app/entities/marketing/marketing-details.vue';
import MarketingService from '../../../../../../main/webapp/app/entities/marketing/marketing.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type MarketingDetailsComponentType = InstanceType<typeof MarketingDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const marketingSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Marketing Management Detail Component', () => {
    let marketingServiceStub: SinonStubbedInstance<MarketingService>;
    let mountOptions: MountingOptions<MarketingDetailsComponentType>['global'];

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
          'router-link': true,
        },
        provide: {
          alertService,
          marketingService: () => marketingServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        marketingServiceStub.find.resolves(marketingSample);
        route = {
          params: {
            marketingId: '' + 123,
          },
        };
        const wrapper = shallowMount(MarketingDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.marketing).toMatchObject(marketingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        marketingServiceStub.find.resolves(marketingSample);
        const wrapper = shallowMount(MarketingDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
