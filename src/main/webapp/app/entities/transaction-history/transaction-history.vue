<template>
  <div>
    <h2 id="page-heading" data-cy="TransactionHistoryHeading">
      <span v-text="t$('storeApp.transactionHistory.home.title')" id="transaction-history-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('storeApp.transactionHistory.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'TransactionHistoryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-transaction-history"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('storeApp.transactionHistory.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && transactionHistories && transactionHistories.length === 0">
      <span v-text="t$('storeApp.transactionHistory.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="transactionHistories && transactionHistories.length > 0">
      <table class="table table-striped" aria-describedby="transactionHistories">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('amount')">
              <span v-text="t$('storeApp.transactionHistory.amount')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'amount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="t$('storeApp.transactionHistory.description')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type')">
              <span v-text="t$('storeApp.transactionHistory.type')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('currency.id')">
              <span v-text="t$('storeApp.transactionHistory.currency')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'currency.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('client.id')">
              <span v-text="t$('storeApp.transactionHistory.client')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'client.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="transactionHistory in transactionHistories" :key="transactionHistory.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TransactionHistoryView', params: { transactionHistoryId: transactionHistory.id } }">{{
                transactionHistory.id
              }}</router-link>
            </td>
            <td>{{ transactionHistory.amount }}</td>
            <td>{{ transactionHistory.description }}</td>
            <td v-text="t$('storeApp.TransactionType.' + transactionHistory.type)"></td>
            <td>
              <div v-if="transactionHistory.currency">
                <router-link :to="{ name: 'CurrencyView', params: { currencyId: transactionHistory.currency.id } }">{{
                  transactionHistory.currency.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="transactionHistory.client">
                <router-link :to="{ name: 'CustomerView', params: { customerId: transactionHistory.client.id } }">{{
                  transactionHistory.client.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'TransactionHistoryView', params: { transactionHistoryId: transactionHistory.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" ></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'TransactionHistoryEdit', params: { transactionHistoryId: transactionHistory.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(transactionHistory)"
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
        <span
          id="storeApp.transactionHistory.delete.question"
          data-cy="transactionHistoryDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="primo-delete-transactionHistory-heading" v-text="t$('storeApp.transactionHistory.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="primo-confirm-delete-transactionHistory"
            data-cy="entityConfirmDeleteButton"

            v-on:click="removeTransactionHistory()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="transactionHistories && transactionHistories.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./transaction-history.component.ts"></script>
