<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="inventory">
        <h2 class="jh-entity-heading" data-cy="inventoryDetailsHeading">
          <span v-text="t$('storeApp.inventory.detail.title')"></span> {{ inventory.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('storeApp.inventory.inStock')"></span>
          </dt>
          <dd>
            <span>{{ inventory.inStock }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.inventory.booked')"></span>
          </dt>
          <dd>
            <span>{{ inventory.booked }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.inventory.claim')"></span>
          </dt>
          <dd>
            <span>{{ inventory.claim }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.inventory.product')"></span>
          </dt>
          <dd>
            <div v-if="inventory.product">
              <router-link :to="{ name: 'ProductView', params: { productId: inventory.product.id } }">{{
                inventory.product.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('storeApp.inventory.unit')"></span>
          </dt>
          <dd>
            <div v-if="inventory.unit">
              <router-link :to="{ name: 'UnitView', params: { unitId: inventory.unit.id } }">{{ inventory.unit.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="inventory.id"
          :to="{ name: 'InventoryEdit', params: { inventoryId: inventory.id } }"
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

<script lang="ts" src="./inventory-details.component.ts"></script>
