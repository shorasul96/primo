/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import PartialObtainDetails from '../../../../../../main/webapp/app/entities/partial-obtain/partial-obtain-details.vue';
import PartialObtainService from '../../../../../../main/webapp/app/entities/partial-obtain/partial-obtain.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type PartialObtainDetailsComponentType = InstanceType<typeof PartialObtainDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const partialObtainSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('PartialObtain Management Detail Component', () => {
    let partialObtainServiceStub: SinonStubbedInstance<PartialObtainService>;
    let mountOptions: MountingOptions<PartialObtainDetailsComponentType>['global'];

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
          'router-link': true,
        },
        provide: {
          alertService,
          partialObtainService: () => partialObtainServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        partialObtainServiceStub.find.resolves(partialObtainSample);
        route = {
          params: {
            partialObtainId: '' + 123,
          },
        };
        const wrapper = shallowMount(PartialObtainDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.partialObtain).toMatchObject(partialObtainSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        partialObtainServiceStub.find.resolves(partialObtainSample);
        const wrapper = shallowMount(PartialObtainDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
