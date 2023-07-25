/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import Marketing from '../../../../../../main/webapp/app/entities/marketing/marketing.vue';
import MarketingService from '../../../../../../main/webapp/app/entities/marketing/marketing.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type MarketingComponentType = InstanceType<typeof Marketing>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Marketing Management Component', () => {
    let marketingServiceStub: SinonStubbedInstance<MarketingService>;
    let mountOptions: MountingOptions<MarketingComponentType>['global'];

    beforeEach(() => {
      marketingServiceStub = sinon.createStubInstance<MarketingService>(MarketingService);
      marketingServiceStub.retrieve.resolves({ headers: {} });

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
          marketingService: () => marketingServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        marketingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Marketing, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(marketingServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.marketings[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(Marketing, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(marketingServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: MarketingComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Marketing, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        marketingServiceStub.retrieve.reset();
        marketingServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        marketingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(marketingServiceStub.retrieve.called).toBeTruthy();
        expect(comp.marketings[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(marketingServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        marketingServiceStub.retrieve.reset();
        marketingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(marketingServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.marketings[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(marketingServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        marketingServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeMarketing();
        await comp.$nextTick(); // clear components

        // THEN
        expect(marketingServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(marketingServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
