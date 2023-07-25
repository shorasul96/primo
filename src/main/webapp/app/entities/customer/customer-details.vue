<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="customer">
        <h2 class="jh-entity-heading" data-cy="customerDetailsHeading">
          <span v-text="t$('storeApp.customer.detail.title')"></span> {{ customer.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('storeApp.customer.fullName')"></span>
          </dt>
          <dd>
            <span>{{ customer.fullName }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.customer.companyName')"></span>
          </dt>
          <dd>
            <span>{{ customer.companyName }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.customer.customerType')"></span>
          </dt>
          <dd>
            <span v-text="t$('storeApp.CustomerType.' + customer.customerType)"></span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="customer.id" :to="{ name: 'CustomerEdit', params: { customerId: customer.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./customer-details.component.ts"></script>
