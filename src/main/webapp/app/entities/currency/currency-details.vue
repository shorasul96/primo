<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="currency">
        <h2 class="jh-entity-heading" data-cy="currencyDetailsHeading">
          <span v-text="t$('storeApp.currency.detail.title')"></span> {{ currency.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('storeApp.currency.name')"></span>
          </dt>
          <dd>
            <span>{{ currency.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.currency.rate')"></span>
          </dt>
          <dd>
            <span>{{ currency.rate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="currency.id" :to="{ name: 'CurrencyEdit', params: { currencyId: currency.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./currency-details.component.ts"></script>
