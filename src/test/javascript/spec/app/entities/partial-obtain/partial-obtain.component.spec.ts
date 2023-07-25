/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import PartialObtain from '../../../../../../main/webapp/app/entities/partial-obtain/partial-obtain.vue';
import PartialObtainService from '../../../../../../main/webapp/app/entities/partial-obtain/partial-obtain.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type PartialObtainComponentType = InstanceType<typeof PartialObtain>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('PartialObtain Management Component', () => {
    let partialObtainServiceStub: SinonStubbedInstance<PartialObtainService>;
    let mountOptions: MountingOptions<PartialObtainComponentType>['global'];

    beforeEach(() => {
      partialObtainServiceStub = sinon.createStubInstance<PartialObtainService>(PartialObtainService);
      partialObtainServiceStub.retrieve.resolves({ headers: {} });

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
          partialObtainService: () => partialObtainServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        partialObtainServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(PartialObtain, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(partialObtainServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.partialObtains[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(PartialObtain, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(partialObtainServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: PartialObtainComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(PartialObtain, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        partialObtainServiceStub.retrieve.reset();
        partialObtainServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        partialObtainServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(partialObtainServiceStub.retrieve.called).toBeTruthy();
        expect(comp.partialObtains[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(partialObtainServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        partialObtainServiceStub.retrieve.reset();
        partialObtainServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(partialObtainServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.partialObtains[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(partialObtainServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        partialObtainServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removePartialObtain();
        await comp.$nextTick(); // clear components

        // THEN
        expect(partialObtainServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(partialObtainServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
