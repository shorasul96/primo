import { Authority } from '@/shared/security/authority';

const PrimoUserManagementComponent = () => import('@/admin/user-management/user-management.vue');
const PrimoUserManagementViewComponent = () => import('@/admin/user-management/user-management-view.vue');
const PrimoUserManagementEditComponent = () => import('@/admin/user-management/user-management-edit.vue');
const PrimoDocsComponent = () => import('@/admin/docs/docs.vue');
const PrimoConfigurationComponent = () => import('@/admin/configuration/configuration.vue');
const PrimoHealthComponent = () => import('@/admin/health/health.vue');
const PrimoLogsComponent = () => import('@/admin/logs/logs.vue');
const PrimoMetricsComponent = () => import('@/admin/metrics/metrics.vue');

export default [
  {
    path: '/admin/user-management',
    name: 'PrimoUser',
    component: PrimoUserManagementComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/new',
    name: 'PrimoUserCreate',
    component: PrimoUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/:userId/edit',
    name: 'PrimoUserEdit',
    component: PrimoUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/:userId/view',
    name: 'PrimoUserView',
    component: PrimoUserManagementViewComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/docs',
    name: 'PrimoDocsComponent',
    component: PrimoDocsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/health',
    name: 'PrimoHealthComponent',
    component: PrimoHealthComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/logs',
    name: 'PrimoLogsComponent',
    component: PrimoLogsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/metrics',
    name: 'PrimoMetricsComponent',
    component: PrimoMetricsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/configuration',
    name: 'PrimoConfigurationComponent',
    component: PrimoConfigurationComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
];
