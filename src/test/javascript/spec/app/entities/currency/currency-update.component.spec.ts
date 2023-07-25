/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import CurrencyUpdate from '../../../../../../main/webapp/app/entities/currency/currency-update.vue';
import CurrencyService from '../../../../../../main/webapp/app/entities/currency/currency.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type CurrencyUpdateComponentType = InstanceType<typeof CurrencyUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const currencySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CurrencyUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Currency Management Update Component', () => {
    let comp: CurrencyUpdateComponentType;
    let currencyServiceStub: SinonStubbedInstance<CurrencyService>;

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
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          currencyService: () => currencyServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(CurrencyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.currency = currencySample;
        currencyServiceStub.update.resolves(currencySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(currencyServiceStub.update.calledWith(currencySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        currencyServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CurrencyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.currency = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(currencyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        currencyServiceStub.find.resolves(currencySample);
        currencyServiceStub.retrieve.resolves([currencySample]);

        // WHEN
        route = {
          params: {
            currencyId: '' + currencySample.id,
          },
        };
        const wrapper = shallowMount(CurrencyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.currency).toMatchObject(currencySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        currencyServiceStub.find.resolves(currencySample);
        const wrapper = shallowMount(CurrencyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
