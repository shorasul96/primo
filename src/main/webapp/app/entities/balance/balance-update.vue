<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="storeApp.balance.home.createOrEditLabel"
          data-cy="BalanceCreateUpdateHeading"
          v-text="t$('storeApp.balance.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="balance.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="balance.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.balance.amount')" for="balance-amount"></label>
            <input
              type="number"
              class="form-control"
              name="amount"
              id="balance-amount"
              data-cy="amount"
              :class="{ valid: !v$.amount.$invalid, invalid: v$.amount.$invalid }"
              v-model.number="v$.amount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.balance.loan')" for="balance-loan"></label>
            <input
              type="number"
              class="form-control"
              name="loan"
              id="balance-loan"
              data-cy="loan"
              :class="{ valid: !v$.loan.$invalid, invalid: v$.loan.$invalid }"
              v-model.number="v$.loan.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.balance.currency')" for="balance-currency"></label>
            <select class="form-control" id="balance-currency" data-cy="currency" name="currency" v-model="balance.currency">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="balance.currency && currencyOption.id === balance.currency.id ? balance.currency : currencyOption"
                v-for="currencyOption in currencies"
                :key="currencyOption.id"
              >
                {{ currencyOption.id }}
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
<script lang="ts" src="./balance-update.component.ts"></script>
