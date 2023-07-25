<template>
  <div>
    <h2 id="page-heading" data-cy="BalanceHeading">
      <span v-text="t$('storeApp.balance.home.title')" id="balance-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('storeApp.balance.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'BalanceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-balance"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('storeApp.balance.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && balances && balances.length === 0">
      <span v-text="t$('storeApp.balance.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="balances && balances.length > 0">
      <table class="table table-striped" aria-describedby="balances">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('amount')">
              <span v-text="t$('storeApp.balance.amount')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'amount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('loan')">
              <span v-text="t$('storeApp.balance.loan')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'loan'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('currency.id')">
              <span v-text="t$('storeApp.balance.currency')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'currency.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="balance in balances" :key="balance.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BalanceView', params: { balanceId: balance.id } }">{{ balance.id }}</router-link>
            </td>
            <td>{{ balance.amount }}</td>
            <td>{{ balance.loan }}</td>
            <td>
              <div v-if="balance.currency">
                <router-link :to="{ name: 'CurrencyView', params: { currencyId: balance.currency.id } }">{{
                  balance.currency.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BalanceView', params: { balanceId: balance.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" ></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BalanceEdit', params: { balanceId: balance.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(balance)"
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
        <span id="storeApp.balance.delete.question" data-cy="balanceDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="primo-delete-balance-heading" v-text="t$('storeApp.balance.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="primo-confirm-delete-balance"
            data-cy="entityConfirmDeleteButton"

            v-on:click="removeBalance()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="balances && balances.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./balance.component.ts"></script>
