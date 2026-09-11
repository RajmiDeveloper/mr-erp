<script setup lang="ts">
import { ref } from 'vue'
import AppDataTable from '@/components/common/AppDataTable.vue'
import type { AppDataTableColumn } from '@/types/dataTable'
import type { ProductCategory } from '@/types/productCategory'

const productCategories = ref<ProductCategory[]>([
  {
    id: 'a4f9e2b1-7c36-4d8a-9f12-0b5c6e7d8f90',
    name: 'Bebidas',
    createdAt: '2026-08-12T10:30:00Z',
  },
  {
    id: 'b8c1d3e5-2f47-4a69-8b10-6c7d9e0f1a23',
    name: 'Almacén',
    createdAt: '2026-08-18T14:45:00Z',
  },
  {
    id: 'c2d4e6f8-3a59-4b70-9c21-7d8e0f1a2b34',
    name: 'Limpieza',
    createdAt: '2026-09-02T09:15:00Z',
  },
])

const columns: AppDataTableColumn<ProductCategory>[] = [
  { field: 'name', header: 'Nombre' },
  { field: 'createdAt', header: 'Fecha de creación', type: 'date' },
]
</script>

<template>
  <section>
    <div class="page-header">
      <div>
        <h2>Categorías de productos</h2>
        <p>Consulta de las categorías asociadas al catálogo de productos.</p>
      </div>
    </div>

    <AppDataTable
      :items="productCategories"
      :columns="columns"
      empty-message="No hay categorías de productos registradas."
      :rows="10"
      :rows-per-page-options="[5, 10, 20, 50]"
    />
  </section>
</template>

<style scoped>
.page-header {
  margin-bottom: 1.5rem;
}

h2 {
  margin: 0;
}

p {
  margin: 0.4rem 0 0;
  color: #6b7280;
}
</style>
