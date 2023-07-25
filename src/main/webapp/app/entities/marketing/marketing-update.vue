<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="storeApp.marketing.home.createOrEditLabel"
          data-cy="MarketingCreateUpdateHeading"
          v-text="t$('storeApp.marketing.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="marketing.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="marketing.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.marketing.deal')" for="marketing-deal"></label>
            <select
              class="form-control"
              name="deal"
              :class="{ valid: !v$.deal.$invalid, invalid: v$.deal.$invalid }"
              v-model="v$.deal.$model"
              id="marketing-deal"
              data-cy="deal"
            >
              <option
                v-for="dealType in dealTypeValues"
                :key="dealType"
                v-bind:value="dealType"
                v-bind:label="t$('storeApp.DealType.' + dealType)"
              >
                {{ dealType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.marketing.customer')" for="marketing-customer"></label>
            <select class="form-control" id="marketing-customer" data-cy="customer" name="customer" v-model="marketing.customer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="marketing.customer && customerOption.id === marketing.customer.id ? marketing.customer : customerOption"
                v-for="customerOption in customers"
                :key="customerOption.id"
              >
                {{ customerOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.marketing.product')" for="marketing-product"></label>
            <select class="form-control" id="marketing-product" data-cy="product" name="product" v-model="marketing.product">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="marketing.product && productOption.id === marketing.product.id ? marketing.product : productOption"
                v-for="productOption in products"
                :key="productOption.id"
              >
                {{ productOption.id }}
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
<script lang="ts" src="./marketing-update.component.ts"></script>
