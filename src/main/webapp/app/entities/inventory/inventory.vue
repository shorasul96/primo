<template>
  <div>
    <h2 id="page-heading" data-cy="InventoryHeading">
      <span v-text="t$('storeApp.inventory.home.title')" id="inventory-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('storeApp.inventory.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'InventoryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-inventory"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('storeApp.inventory.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && inventories && inventories.length === 0">
      <span v-text="t$('storeApp.inventory.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="inventories && inventories.length > 0">
      <table class="table table-striped" aria-describedby="inventories">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('inStock')">
              <span v-text="t$('storeApp.inventory.inStock')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'inStock'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('booked')">
              <span v-text="t$('storeApp.inventory.booked')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'booked'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('claim')">
              <span v-text="t$('storeApp.inventory.claim')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'claim'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('product.id')">
              <span v-text="t$('storeApp.inventory.product')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'product.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('unit.id')">
              <span v-text="t$('storeApp.inventory.unit')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'unit.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="inventory in inventories" :key="inventory.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'InventoryView', params: { inventoryId: inventory.id } }">{{ inventory.id }}</router-link>
            </td>
            <td>{{ inventory.inStock }}</td>
            <td>{{ inventory.booked }}</td>
            <td>{{ inventory.claim }}</td>
            <td>
              <div v-if="inventory.product">
                <router-link :to="{ name: 'ProductView', params: { productId: inventory.product.id } }">{{
                  inventory.product.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="inventory.unit">
                <router-link :to="{ name: 'UnitView', params: { unitId: inventory.unit.id } }">{{ inventory.unit.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'InventoryView', params: { inventoryId: inventory.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" ></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'InventoryEdit', params: { inventoryId: inventory.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(inventory)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="storeApp.inventory.delete.question" data-cy="inventoryDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="primo-delete-inventory-heading" v-text="t$('storeApp.inventory.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="primo-confirm-delete-inventory"
            data-cy="entityConfirmDeleteButton"

            v-on:click="removeInventory()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="inventories && inventories.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./inventory.component.ts"></script>
