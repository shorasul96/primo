<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="storeApp.customer.home.createOrEditLabel"
          data-cy="CustomerCreateUpdateHeading"
          v-text="t$('storeApp.customer.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="customer.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="customer.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.customer.fullName')" for="customer-fullName"></label>
            <input
              type="text"
              class="form-control"
              name="fullName"
              id="customer-fullName"
              data-cy="fullName"
              :class="{ valid: !v$.fullName.$invalid, invalid: v$.fullName.$invalid }"
              v-model="v$.fullName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.customer.companyName')" for="customer-companyName"></label>
            <input
              type="text"
              class="form-control"
              name="companyName"
              id="customer-companyName"
              data-cy="companyName"
              :class="{ valid: !v$.companyName.$invalid, invalid: v$.companyName.$invalid }"
              v-model="v$.companyName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.customer.customerType')" for="customer-customerType"></label>
            <select
              class="form-control"
              name="customerType"
              :class="{ valid: !v$.customerType.$invalid, invalid: v$.customerType.$invalid }"
              v-model="v$.customerType.$model"
              id="customer-customerType"
              data-cy="customerType"
            >
              <option
                v-for="customerType in customerTypeValues"
                :key="customerType"
                v-bind:value="customerType"
                v-bind:label="t$('storeApp.CustomerType.' + customerType)"
              >
                {{ customerType }}
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
<script lang="ts" src="./customer-update.component.ts"></script>
