<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="storeApp.unit.home.createOrEditLabel"
          data-cy="UnitCreateUpdateHeading"
          v-text="t$('storeApp.unit.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="unit.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="unit.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.unit.measurement')" for="unit-measurement"></label>
            <input
              type="text"
              class="form-control"
              name="measurement"
              id="unit-measurement"
              data-cy="measurement"
              :class="{ valid: !v$.measurement.$invalid, invalid: v$.measurement.$invalid }"
              v-model="v$.measurement.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('storeApp.unit.description')" for="unit-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="unit-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
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
<script lang="ts" src="./unit-update.component.ts"></script>
