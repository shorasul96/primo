<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="marketing">
        <h2 class="jh-entity-heading" data-cy="marketingDetailsHeading">
          <span v-text="t$('storeApp.marketing.detail.title')"></span> {{ marketing.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('storeApp.marketing.deal')"></span>
          </dt>
          <dd>
            <span v-text="t$('storeApp.DealType.' + marketing.deal)"></span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.marketing.customer')"></span>
          </dt>
          <dd>
            <div v-if="marketing.customer">
              <router-link :to="{ name: 'CustomerView', params: { customerId: marketing.customer.id } }">{{
                marketing.customer.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('storeApp.marketing.product')"></span>
          </dt>
          <dd>
            <div v-if="marketing.product">
              <router-link :to="{ name: 'ProductView', params: { productId: marketing.product.id } }">{{
                marketing.product.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="marketing.id"
          :to="{ name: 'MarketingEdit', params: { marketingId: marketing.id } }"
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

<script lang="ts" src="./marketing-details.component.ts"></script>
