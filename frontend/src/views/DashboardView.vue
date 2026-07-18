<script setup lang="ts">
import Card from 'primevue/card'
import { RouterLink } from 'vue-router'
</script>

<template>
  <section>
    <h2>Panel principal</h2>

    <div class="cards">
      <RouterLink class="product-card" to="/products">
        <Card>
          <template #title>Productos</template>
          <template #content>
            <p>Administración del catálogo de productos.</p>
          </template>
        </Card>
      </RouterLink>

      <Card>
        <template #title>Stock</template>
        <template #content>
          <p>Control de existencias y movimientos.</p>
        </template>
      </Card>

      <Card>
        <template #title>Ventas</template>
        <template #content>
          <p>Registro y seguimiento de ventas.</p>
        </template>
      </Card>
    </div>
  </section>
</template>

<style scoped>
h2 {
  margin-top: 0;
}

.cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
  gap: 1rem;
  margin-top: 1.5rem;
}

.product-card {
  color: inherit;
  text-decoration: none;
}
</style>
