<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="partialObtain">
        <h2 class="jh-entity-heading" data-cy="partialObtainDetailsHeading">
          <span v-text="t$('storeApp.partialObtain.detail.title')"></span> {{ partialObtain.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('storeApp.partialObtain.unit')"></span>
          </dt>
          <dd>
            <div v-if="partialObtain.unit">
              <router-link :to="{ name: 'UnitView', params: { unitId: partialObtain.unit.id } }">{{ partialObtain.unit.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('storeApp.partialObtain.customer')"></span>
          </dt>
          <dd>
            <div v-if="partialObtain.customer">
              <router-link :to="{ name: 'CustomerView', params: { customerId: partialObtain.customer.id } }">{{
                partialObtain.customer.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('storeApp.partialObtain.item')"></span>
          </dt>
          <dd>
            <div v-if="partialObtain.item">
              <router-link :to="{ name: 'InventoryView', params: { inventoryId: partialObtain.item.id } }">{{
                partialObtain.item.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="partialObtain.id"
          :to="{ name: 'PartialObtainEdit', params: { partialObtainId: partialObtain.id } }"
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

<script lang="ts" src="./partial-obtain-details.component.ts"></script>
