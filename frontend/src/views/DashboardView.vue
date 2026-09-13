<script setup lang="ts">
import Card from 'primevue/card'
import { RouterLink } from 'vue-router'
</script>

<template>
  <section>
    <h2>Panel principal</h2>

    <div class="access-groups">
      <section class="access-group">
        <h3>Gestión principal</h3>

        <div class="cards primary-cards">
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

          <RouterLink class="sales-card" to="/sales/new">
            <Card>
              <template #title>Ventas</template>
              <template #content>
                <p>Registro y seguimiento de ventas.</p>
              </template>
            </Card>
          </RouterLink>

          <RouterLink class="customer-card" to="/customers">
            <Card>
              <template #title>Clientes</template>
              <template #content>
                <p>Administración del catálogo de clientes.</p>
              </template>
            </Card>
          </RouterLink>
        </div>
      </section>

      <section class="access-group">
        <h3>Configuración</h3>

        <div class="cards secondary-cards">
          <RouterLink class="category-card" to="/product-categories">
            <Card>
              <template #title>Categorías</template>
              <template #content>
                <p>Consulta de categorías de productos.</p>
              </template>
            </Card>
          </RouterLink>

          <RouterLink class="category-card" to="/customer-categories">
            <Card>
              <template #title>Categorías de clientes</template>
              <template #content>
                <p>Configuración de categorías para clientes.</p>
              </template>
            </Card>
          </RouterLink>
        </div>
      </section>
    </div>
  </section>
</template>

<style scoped>
h2 {
  margin-top: 0;
}

.access-groups {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-top: 1.5rem;
}

.access-group {
  min-width: 0;
  padding: 1rem;
  background: #e5e7eb;
  border-radius: 8px;
}

h3 {
  margin: 0 0 1rem;
  font-size: 1rem;
}

.cards {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 1rem;
}

.primary-cards {
  grid-template-columns: repeat(3, minmax(0, 1fr));
}

.secondary-cards {
  grid-template-columns: repeat(4, minmax(0, 1fr));
}

.product-card {
  color: inherit;
  text-decoration: none;
}

.sales-card {
  color: inherit;
  text-decoration: none;
}

.category-card {
  color: inherit;
  text-decoration: none;
}

.customer-card {
  color: inherit;
  text-decoration: none;
}

@media (max-width: 900px) {
  .primary-cards,
  .secondary-cards {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 600px) {
  .cards {
    grid-template-columns: 1fr;
  }
}
</style>
