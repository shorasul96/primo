/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import BalanceDetails from '../../../../../../main/webapp/app/entities/balance/balance-details.vue';
import BalanceService from '../../../../../../main/webapp/app/entities/balance/balance.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type BalanceDetailsComponentType = InstanceType<typeof BalanceDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const balanceSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Balance Management Detail Component', () => {
    let balanceServiceStub: SinonStubbedInstance<BalanceService>;
    let mountOptions: MountingOptions<BalanceDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      balanceServiceStub = sinon.createStubInstance<BalanceService>(BalanceService);

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
          balanceService: () => balanceServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        balanceServiceStub.find.resolves(balanceSample);
        route = {
          params: {
            balanceId: '' + 123,
          },
        };
        const wrapper = shallowMount(BalanceDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.balance).toMatchObject(balanceSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        balanceServiceStub.find.resolves(balanceSample);
        const wrapper = shallowMount(BalanceDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
