<template>
  <div>
    <h2 id="page-heading" data-cy="CurrencyHeading">
      <span v-text="t$('storeApp.currency.home.title')" id="currency-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('storeApp.currency.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CurrencyCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-currency"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('storeApp.currency.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && currencies && currencies.length === 0">
      <span v-text="t$('storeApp.currency.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="currencies && currencies.length > 0">
      <table class="table table-striped" aria-describedby="currencies">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('storeApp.currency.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('rate')">
              <span v-text="t$('storeApp.currency.rate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'rate'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="currency in currencies" :key="currency.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CurrencyView', params: { currencyId: currency.id } }">{{ currency.id }}</router-link>
            </td>
            <td>{{ currency.name }}</td>
            <td>{{ currency.rate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CurrencyView', params: { currencyId: currency.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" ></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CurrencyEdit', params: { currencyId: currency.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(currency)"
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
        <span id="storeApp.currency.delete.question" data-cy="currencyDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="primo-delete-currency-heading" v-text="t$('storeApp.currency.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="primo-confirm-delete-currency"
            data-cy="entityConfirmDeleteButton"

            v-on:click="removeCurrency()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="currencies && currencies.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./currency.component.ts"></script>
