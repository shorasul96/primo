/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import CategoryUpdate from '../../../../../../main/webapp/app/entities/category/category-update.vue';
import CategoryService from '../../../../../../main/webapp/app/entities/category/category.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

import CategoryService from '../../../../../../main/webapp/app/entities/category/category.service';

type CategoryUpdateComponentType = InstanceType<typeof CategoryUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const categorySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CategoryUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Category Management Update Component', () => {
    let comp: CategoryUpdateComponentType;
    let categoryServiceStub: SinonStubbedInstance<CategoryService>;

    beforeEach(() => {
      route = {};
      categoryServiceStub = sinon.createStubInstance<CategoryService>(CategoryService);

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
          categoryService: () => categoryServiceStub,
          categoryService: () =>
            sinon.createStubInstance<CategoryService>(CategoryService, {
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
        const wrapper = shallowMount(CategoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.category = categorySample;
        categoryServiceStub.update.resolves(categorySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(categoryServiceStub.update.calledWith(categorySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        categoryServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CategoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.category = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(categoryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        categoryServiceStub.find.resolves(categorySample);
        categoryServiceStub.retrieve.resolves([categorySample]);

        // WHEN
        route = {
          params: {
            categoryId: '' + categorySample.id,
          },
        };
        const wrapper = shallowMount(CategoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.category).toMatchObject(categorySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        categoryServiceStub.find.resolves(categorySample);
        const wrapper = shallowMount(CategoryUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
