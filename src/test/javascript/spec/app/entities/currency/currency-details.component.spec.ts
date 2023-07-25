/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import CurrencyDetails from '../../../../../../main/webapp/app/entities/currency/currency-details.vue';
import CurrencyService from '../../../../../../main/webapp/app/entities/currency/currency.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type CurrencyDetailsComponentType = InstanceType<typeof CurrencyDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const currencySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Currency Management Detail Component', () => {
    let currencyServiceStub: SinonStubbedInstance<CurrencyService>;
    let mountOptions: MountingOptions<CurrencyDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      currencyServiceStub = sinon.createStubInstance<CurrencyService>(CurrencyService);

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
          currencyService: () => currencyServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        currencyServiceStub.find.resolves(currencySample);
        route = {
          params: {
            currencyId: '' + 123,
          },
        };
        const wrapper = shallowMount(CurrencyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.currency).toMatchObject(currencySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        currencyServiceStub.find.resolves(currencySample);
        const wrapper = shallowMount(CurrencyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
