<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="storeApp.transactionHistory.home.createOrEditLabel"
          data-cy="TransactionHistoryCreateUpdateHeading"
          v-text="t$('storeApp.transactionHistory.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="transactionHistory.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="transactionHistory.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.transactionHistory.amount')" for="transaction-history-amount"></label>
            <input
              type="number"
              class="form-control"
              name="amount"
              id="transaction-history-amount"
              data-cy="amount"
              :class="{ valid: !v$.amount.$invalid, invalid: v$.amount.$invalid }"
              v-model.number="v$.amount.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('storeApp.transactionHistory.description')"
              for="transaction-history-description"
            ></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="transaction-history-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.transactionHistory.type')" for="transaction-history-type"></label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !v$.type.$invalid, invalid: v$.type.$invalid }"
              v-model="v$.type.$model"
              id="transaction-history-type"
              data-cy="type"
            >
              <option
                v-for="transactionType in transactionTypeValues"
                :key="transactionType"
                v-bind:value="transactionType"
                v-bind:label="t$('storeApp.TransactionType.' + transactionType)"
              >
                {{ transactionType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('storeApp.transactionHistory.currency')"
              for="transaction-history-currency"
            ></label>
            <select
              class="form-control"
              id="transaction-history-currency"
              data-cy="currency"
              name="currency"
              v-model="transactionHistory.currency"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  transactionHistory.currency && currencyOption.id === transactionHistory.currency.id
                    ? transactionHistory.currency
                    : currencyOption
                "
                v-for="currencyOption in currencies"
                :key="currencyOption.id"
              >
                {{ currencyOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.transactionHistory.client')" for="transaction-history-client"></label>
            <select class="form-control" id="transaction-history-client" data-cy="client" name="client" v-model="transactionHistory.client">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  transactionHistory.client && customerOption.id === transactionHistory.client.id
                    ? transactionHistory.client
                    : customerOption
                "
                v-for="customerOption in customers"
                :key="customerOption.id"
              >
                {{ customerOption.id }}
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
<script lang="ts" src="./transaction-history-update.component.ts"></script>
