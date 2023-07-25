<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="category">
        <h2 class="jh-entity-heading" data-cy="categoryDetailsHeading">
          <span v-text="t$('storeApp.category.detail.title')"></span> {{ category.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('storeApp.category.name')"></span>
          </dt>
          <dd>
            <span>{{ category.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.category.description')"></span>
          </dt>
          <dd>
            <span>{{ category.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('storeApp.category.parent')"></span>
          </dt>
          <dd>
            <div v-if="category.parent">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: category.parent.id } }">{{ category.parent.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="category.id" :to="{ name: 'CategoryEdit', params: { categoryId: category.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./category-details.component.ts"></script>
