<template>
  <div>
    <b-dropdown-item to="/marketing">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.marketing')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/manufacture-stage">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.manufactureStage')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/unit">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.unit')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/inventory">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.inventory')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/partial-obtain">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.partialObtain')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/product">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.product')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/category">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.category')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/balance">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.balance')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/transaction-history">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.transactionHistory')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/currency">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.currency')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/customer">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.customer')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
