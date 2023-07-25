<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="storeApp.product.home.createOrEditLabel"
          data-cy="ProductCreateUpdateHeading"
          v-text="t$('storeApp.product.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="product.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="product.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.product.name')" for="product-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="product-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.product.description')" for="product-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="product-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.product.stage')" for="product-stage"></label>
            <select class="form-control" id="product-stage" data-cy="stage" name="stage" v-model="product.stage">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="product.stage && manufactureStageOption.id === product.stage.id ? product.stage : manufactureStageOption"
                v-for="manufactureStageOption in manufactureStages"
                :key="manufactureStageOption.id"
              >
                {{ manufactureStageOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.product.category')" for="product-category"></label>
            <select class="form-control" id="product-category" data-cy="category" name="category" v-model="product.category">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="product.category && categoryOption.id === product.category.id ? product.category : categoryOption"
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
<script lang="ts" src="./product-update.component.ts"></script>
