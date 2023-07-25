<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="product">
        <h2 class="jh-entity-heading" data-cy="productDetailsHeading">
          <span v-text="t$('storeApp.product.detail.title')"></span> {{ product.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('storeApp.product.name')"></span>
          </dt>
          <dd>
            <span>{{ product.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.product.description')"></span>
          </dt>
          <dd>
            <span>{{ product.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.product.stage')"></span>
          </dt>
          <dd>
            <div v-if="product.stage">
              <router-link :to="{ name: 'ManufactureStageView', params: { manufactureStageId: product.stage.id } }">{{
                product.stage.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('storeApp.product.category')"></span>
          </dt>
          <dd>
            <div v-if="product.category">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: product.category.id } }">{{
                product.category.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="product.id" :to="{ name: 'ProductEdit', params: { productId: product.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product-details.component.ts"></script>
