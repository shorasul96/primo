/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import ManufactureStage from '../../../../../../main/webapp/app/entities/manufacture-stage/manufacture-stage.vue';
import ManufactureStageService from '../../../../../../main/webapp/app/entities/manufacture-stage/manufacture-stage.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type ManufactureStageComponentType = InstanceType<typeof ManufactureStage>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ManufactureStage Management Component', () => {
    let manufactureStageServiceStub: SinonStubbedInstance<ManufactureStageService>;
    let mountOptions: MountingOptions<ManufactureStageComponentType>['global'];

    beforeEach(() => {
      manufactureStageServiceStub = sinon.createStubInstance<ManufactureStageService>(ManufactureStageService);
      manufactureStageServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          manufactureStageService: () => manufactureStageServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        manufactureStageServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(ManufactureStage, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(manufactureStageServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.manufactureStages[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(ManufactureStage, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(manufactureStageServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: ManufactureStageComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ManufactureStage, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        manufactureStageServiceStub.retrieve.reset();
        manufactureStageServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        manufactureStageServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(manufactureStageServiceStub.retrieve.called).toBeTruthy();
        expect(comp.manufactureStages[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(manufactureStageServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        manufactureStageServiceStub.retrieve.reset();
        manufactureStageServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(manufactureStageServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.manufactureStages[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(manufactureStageServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        manufactureStageServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeManufactureStage();
        await comp.$nextTick(); // clear components

        // THEN
        expect(manufactureStageServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(manufactureStageServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
