<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="storeApp.category.home.createOrEditLabel"
          data-cy="CategoryCreateUpdateHeading"
          v-text="t$('storeApp.category.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="category.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="category.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.category.name')" for="category-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="category-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.category.description')" for="category-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="category-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.category.parent')" for="category-parent"></label>
            <select class="form-control" id="category-parent" data-cy="parent" name="parent" v-model="category.parent">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="category.parent && categoryOption.id === category.parent.id ? category.parent : categoryOption"
                v-for="categoryOption in categories"
                :key="categoryOption.id"
              >
                {{ categoryOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./category-update.component.ts"></script>
