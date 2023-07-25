<template>
  <div>
    <h2 id="page-heading" data-cy="PartialObtainHeading">
      <span v-text="t$('storeApp.partialObtain.home.title')" id="partial-obtain-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('storeApp.partialObtain.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PartialObtainCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-partial-obtain"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('storeApp.partialObtain.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && partialObtains && partialObtains.length === 0">
      <span v-text="t$('storeApp.partialObtain.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="partialObtains && partialObtains.length > 0">
      <table class="table table-striped" aria-describedby="partialObtains">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('unit.id')">
              <span v-text="t$('storeApp.partialObtain.unit')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'unit.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customer.id')">
              <span v-text="t$('storeApp.partialObtain.customer')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customer.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('item.id')">
              <span v-text="t$('storeApp.partialObtain.item')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'item.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="partialObtain in partialObtains" :key="partialObtain.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PartialObtainView', params: { partialObtainId: partialObtain.id } }">{{
                partialObtain.id
              }}</router-link>
            </td>
            <td>
              <div v-if="partialObtain.unit">
                <router-link :to="{ name: 'UnitView', params: { unitId: partialObtain.unit.id } }">{{ partialObtain.unit.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="partialObtain.customer">
                <router-link :to="{ name: 'CustomerView', params: { customerId: partialObtain.customer.id } }">{{
                  partialObtain.customer.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="partialObtain.item">
                <router-link :to="{ name: 'InventoryView', params: { inventoryId: partialObtain.item.id } }">{{
                  partialObtain.item.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PartialObtainView', params: { partialObtainId: partialObtain.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'PartialObtainEdit', params: { partialObtainId: partialObtain.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(partialObtain)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="storeApp.partialObtain.delete.question"
          data-cy="partialObtainDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="primo-delete-partialObtain-heading" v-text="t$('storeApp.partialObtain.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="primo-confirm-delete-partialObtain"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removePartialObtain()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="partialObtains && partialObtains.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./partial-obtain.component.ts"></script>
