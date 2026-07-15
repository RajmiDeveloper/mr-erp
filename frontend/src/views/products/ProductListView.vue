<script setup lang="ts">
import Button from 'primevue/button'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import { ref } from 'vue'

interface Product {
  id: string
  code: string
  name: string
  salePrice: number
  active: boolean
}

const products = ref<Product[]>([
  {
    id: '1',
    code: 'PLA-NEGRO-001',
    name: 'Filamento PLA negro 1 kg',
    salePrice: 18000,
    active: true,
  },
])
</script>

<template>
  <section>
    <div class="page-header">
      <div>
        <h2>Productos</h2>
        <p>Administración del catálogo de productos.</p>
      </div>

      <RouterLink to="/products/new">
        <Button label="Nuevo producto" icon="pi pi-plus" />
      </RouterLink>
    </div>

    <DataTable :value="products" striped-rows>
      <Column field="code" header="Código" />
      <Column field="name" header="Nombre" />
      <Column field="salePrice" header="Precio de venta" />

      <Column header="Estado">
        <template #body="{ data }">
          {{ data.active ? 'Activo' : 'Inactivo' }}
        </template>
      </Column>
    </DataTable>
  </section>
</template>

<style scoped>
.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1.5rem;
}

h2 {
  margin: 0;
}

p {
  margin: 0.4rem 0 0;
  color: #6b7280;
}

a {
  text-decoration: none;
}
</style>
