<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="transactionHistory">
        <h2 class="jh-entity-heading" data-cy="transactionHistoryDetailsHeading">
          <span v-text="t$('storeApp.transactionHistory.detail.title')"></span> {{ transactionHistory.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('storeApp.transactionHistory.amount')"></span>
          </dt>
          <dd>
            <span>{{ transactionHistory.amount }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.transactionHistory.description')"></span>
          </dt>
          <dd>
            <span>{{ transactionHistory.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.transactionHistory.type')"></span>
          </dt>
          <dd>
            <span v-text="t$('storeApp.TransactionType.' + transactionHistory.type)"></span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.transactionHistory.currency')"></span>
          </dt>
          <dd>
            <div v-if="transactionHistory.currency">
              <router-link :to="{ name: 'CurrencyView', params: { currencyId: transactionHistory.currency.id } }">{{
                transactionHistory.currency.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('storeApp.transactionHistory.client')"></span>
          </dt>
          <dd>
            <div v-if="transactionHistory.client">
              <router-link :to="{ name: 'CustomerView', params: { customerId: transactionHistory.client.id } }">{{
                transactionHistory.client.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="transactionHistory.id"
          :to="{ name: 'TransactionHistoryEdit', params: { transactionHistoryId: transactionHistory.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./transaction-history-details.component.ts"></script>
