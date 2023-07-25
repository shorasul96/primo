/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import CategoryDetails from '../../../../../../main/webapp/app/entities/category/category-details.vue';
import CategoryService from '../../../../../../main/webapp/app/entities/category/category.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

type CategoryDetailsComponentType = InstanceType<typeof CategoryDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const categorySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Category Management Detail Component', () => {
    let categoryServiceStub: SinonStubbedInstance<CategoryService>;
    let mountOptions: MountingOptions<CategoryDetailsComponentType>['global'];

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
          'router-link': true,
        },
        provide: {
          alertService,
          categoryService: () => categoryServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        categoryServiceStub.find.resolves(categorySample);
        route = {
          params: {
            categoryId: '' + 123,
          },
        };
        const wrapper = shallowMount(CategoryDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.category).toMatchObject(categorySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        categoryServiceStub.find.resolves(categorySample);
        const wrapper = shallowMount(CategoryDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
