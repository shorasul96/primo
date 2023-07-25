/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import ManufactureStageUpdate from '../../../../../../main/webapp/app/entities/manufacture-stage/manufacture-stage-update.vue';
import ManufactureStageService from '../../../../../../main/webapp/app/entities/manufacture-stage/manufacture-stage.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type ManufactureStageUpdateComponentType = InstanceType<typeof ManufactureStageUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const manufactureStageSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ManufactureStageUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ManufactureStage Management Update Component', () => {
    let comp: ManufactureStageUpdateComponentType;
    let manufactureStageServiceStub: SinonStubbedInstance<ManufactureStageService>;

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
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          manufactureStageService: () => manufactureStageServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ManufactureStageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.manufactureStage = manufactureStageSample;
        manufactureStageServiceStub.update.resolves(manufactureStageSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(manufactureStageServiceStub.update.calledWith(manufactureStageSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        manufactureStageServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ManufactureStageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.manufactureStage = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(manufactureStageServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        manufactureStageServiceStub.find.resolves(manufactureStageSample);
        manufactureStageServiceStub.retrieve.resolves([manufactureStageSample]);

        // WHEN
        route = {
          params: {
            manufactureStageId: '' + manufactureStageSample.id,
          },
        };
        const wrapper = shallowMount(ManufactureStageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.manufactureStage).toMatchObject(manufactureStageSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        manufactureStageServiceStub.find.resolves(manufactureStageSample);
        const wrapper = shallowMount(ManufactureStageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
