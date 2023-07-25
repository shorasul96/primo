<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="unit">
        <h2 class="jh-entity-heading" data-cy="unitDetailsHeading">
          <span v-text="t$('storeApp.unit.detail.title')"></span> {{ unit.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('storeApp.unit.measurement')"></span>
          </dt>
          <dd>
            <span>{{ unit.measurement }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.unit.description')"></span>
          </dt>
          <dd>
            <span>{{ unit.description }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="unit.id" :to="{ name: 'UnitEdit', params: { unitId: unit.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./unit-details.component.ts"></script>
