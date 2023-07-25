/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import ManufactureStageDetails from '../../../../../../main/webapp/app/entities/manufacture-stage/manufacture-stage-details.vue';
import ManufactureStageService from '../../../../../../main/webapp/app/entities/manufacture-stage/manufacture-stage.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type ManufactureStageDetailsComponentType = InstanceType<typeof ManufactureStageDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const manufactureStageSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ManufactureStage Management Detail Component', () => {
    let manufactureStageServiceStub: SinonStubbedInstance<ManufactureStageService>;
    let mountOptions: MountingOptions<ManufactureStageDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      manufactureStageServiceStub = sinon.createStubInstance<ManufactureStageService>(ManufactureStageService);

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
          manufactureStageService: () => manufactureStageServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        manufactureStageServiceStub.find.resolves(manufactureStageSample);
        route = {
          params: {
            manufactureStageId: '' + 123,
          },
        };
        const wrapper = shallowMount(ManufactureStageDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.manufactureStage).toMatchObject(manufactureStageSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        manufactureStageServiceStub.find.resolves(manufactureStageSample);
        const wrapper = shallowMount(ManufactureStageDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
