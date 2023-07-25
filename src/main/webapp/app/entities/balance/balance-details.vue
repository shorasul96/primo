<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="balance">
        <h2 class="jh-entity-heading" data-cy="balanceDetailsHeading">
          <span v-text="t$('storeApp.balance.detail.title')"></span> {{ balance.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('storeApp.balance.amount')"></span>
          </dt>
          <dd>
            <span>{{ balance.amount }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.balance.loan')"></span>
          </dt>
          <dd>
            <span>{{ balance.loan }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.balance.currency')"></span>
          </dt>
          <dd>
            <div v-if="balance.currency">
              <router-link :to="{ name: 'CurrencyView', params: { currencyId: balance.currency.id } }">{{
                balance.currency.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="balance.id" :to="{ name: 'BalanceEdit', params: { balanceId: balance.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./balance-details.component.ts"></script>
