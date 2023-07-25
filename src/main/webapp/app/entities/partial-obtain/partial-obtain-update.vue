<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="storeApp.partialObtain.home.createOrEditLabel"
          data-cy="PartialObtainCreateUpdateHeading"
          v-text="t$('storeApp.partialObtain.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="partialObtain.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="partialObtain.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.partialObtain.unit')" for="partial-obtain-unit"></label>
            <select class="form-control" id="partial-obtain-unit" data-cy="unit" name="unit" v-model="partialObtain.unit">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="partialObtain.unit && unitOption.id === partialObtain.unit.id ? partialObtain.unit : unitOption"
                v-for="unitOption in units"
                :key="unitOption.id"
              >
                {{ unitOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.partialObtain.customer')" for="partial-obtain-customer"></label>
            <select class="form-control" id="partial-obtain-customer" data-cy="customer" name="customer" v-model="partialObtain.customer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  partialObtain.customer && customerOption.id === partialObtain.customer.id ? partialObtain.customer : customerOption
                "
                v-for="customerOption in customers"
                :key="customerOption.id"
              >
                {{ customerOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.partialObtain.item')" for="partial-obtain-item"></label>
            <select class="form-control" id="partial-obtain-item" data-cy="item" name="item" v-model="partialObtain.item">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="partialObtain.item && inventoryOption.id === partialObtain.item.id ? partialObtain.item : inventoryOption"
                v-for="inventoryOption in inventories"
                :key="inventoryOption.id"
              >
                {{ inventoryOption.id }}
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
<script lang="ts" src="./partial-obtain-update.component.ts"></script>
