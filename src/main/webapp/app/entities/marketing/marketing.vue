<template>
  <div>
    <h2 id="page-heading" data-cy="MarketingHeading">
      <span v-text="t$('storeApp.marketing.home.title')" id="marketing-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('storeApp.marketing.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MarketingCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-marketing"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('storeApp.marketing.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && marketings && marketings.length === 0">
      <span v-text="t$('storeApp.marketing.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="marketings && marketings.length > 0">
      <table class="table table-striped" aria-describedby="marketings">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('deal')">
              <span v-text="t$('storeApp.marketing.deal')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deal'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customer.id')">
              <span v-text="t$('storeApp.marketing.customer')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customer.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('product.id')">
              <span v-text="t$('storeApp.marketing.product')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'product.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="marketing in marketings" :key="marketing.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MarketingView', params: { marketingId: marketing.id } }">{{ marketing.id }}</router-link>
            </td>
            <td v-text="t$('storeApp.DealType.' + marketing.deal)"></td>
            <td>
              <div v-if="marketing.customer">
                <router-link :to="{ name: 'CustomerView', params: { customerId: marketing.customer.id } }">{{
                  marketing.customer.fullName + ' ' + marketing.customer.companyName
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="marketing.product">
                <router-link :to="{ name: 'ProductView', params: { productId: marketing.product.id } }">{{
                  marketing.product.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MarketingView', params: { marketingId: marketing.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" ></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MarketingEdit', params: { marketingId: marketing.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(marketing)"
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
        <span id="storeApp.marketing.delete.question" data-cy="marketingDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="primo-delete-marketing-heading" v-text="t$('storeApp.marketing.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="primo-confirm-delete-marketing"
            data-cy="entityConfirmDeleteButton"

            v-on:click="removeMarketing()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="marketings && marketings.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./marketing.component.ts"></script>
