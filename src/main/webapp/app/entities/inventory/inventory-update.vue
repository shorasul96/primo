<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="storeApp.inventory.home.createOrEditLabel"
          data-cy="InventoryCreateUpdateHeading"
          v-text="t$('storeApp.inventory.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="inventory.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="inventory.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.inventory.inStock')" for="inventory-inStock"></label>
            <input
              type="number"
              class="form-control"
              name="inStock"
              id="inventory-inStock"
              data-cy="inStock"
              :class="{ valid: !v$.inStock.$invalid, invalid: v$.inStock.$invalid }"
              v-model.number="v$.inStock.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.inventory.booked')" for="inventory-booked"></label>
            <input
              type="number"
              class="form-control"
              name="booked"
              id="inventory-booked"
              data-cy="booked"
              :class="{ valid: !v$.booked.$invalid, invalid: v$.booked.$invalid }"
              v-model.number="v$.booked.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.inventory.claim')" for="inventory-claim"></label>
            <input
              type="number"
              class="form-control"
              name="claim"
              id="inventory-claim"
              data-cy="claim"
              :class="{ valid: !v$.claim.$invalid, invalid: v$.claim.$invalid }"
              v-model.number="v$.claim.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.inventory.product')" for="inventory-product"></label>
            <select class="form-control" id="inventory-product" data-cy="product" name="product" v-model="inventory.product">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="inventory.product && productOption.id === inventory.product.id ? inventory.product : productOption"
                v-for="productOption in products"
                :key="productOption.id"
              >
                {{ productOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.inventory.unit')" for="inventory-unit"></label>
            <select class="form-control" id="inventory-unit" data-cy="unit" name="unit" v-model="inventory.unit">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="inventory.unit && unitOption.id === inventory.unit.id ? inventory.unit : unitOption"
                v-for="unitOption in units"
                :key="unitOption.id"
              >
                {{ unitOption.measurement }}
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
<script lang="ts" src="./inventory-update.component.ts"></script>
