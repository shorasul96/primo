/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import BalanceUpdate from '../../../../../../main/webapp/app/entities/balance/balance-update.vue';
import BalanceService from '../../../../../../main/webapp/app/entities/balance/balance.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

import CurrencyService from '../../../../../../main/webapp/app/entities/currency/currency.service';

type BalanceUpdateComponentType = InstanceType<typeof BalanceUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const balanceSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<BalanceUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Balance Management Update Component', () => {
    let comp: BalanceUpdateComponentType;
    let balanceServiceStub: SinonStubbedInstance<BalanceService>;

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
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          balanceService: () => balanceServiceStub,
          currencyService: () =>
            sinon.createStubInstance<CurrencyService>(CurrencyService, {
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
        const wrapper = shallowMount(BalanceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.balance = balanceSample;
        balanceServiceStub.update.resolves(balanceSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(balanceServiceStub.update.calledWith(balanceSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        balanceServiceStub.create.resolves(entity);
        const wrapper = shallowMount(BalanceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.balance = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(balanceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        balanceServiceStub.find.resolves(balanceSample);
        balanceServiceStub.retrieve.resolves([balanceSample]);

        // WHEN
        route = {
          params: {
            balanceId: '' + balanceSample.id,
          },
        };
        const wrapper = shallowMount(BalanceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.balance).toMatchObject(balanceSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        balanceServiceStub.find.resolves(balanceSample);
        const wrapper = shallowMount(BalanceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
