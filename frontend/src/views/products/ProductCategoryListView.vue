<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Button from 'primevue/button'
import {
  createProductCategory,
  getProductCategories,
} from '@/api/productCategoryService'
import AppDataTable from '@/components/common/AppDataTable.vue'
import CategoryQuickEntryDialog from '@/components/common/CategoryQuickEntryDialog.vue'
import type { AppDataTableColumn } from '@/types/dataTable'
import type { ProductCategory } from '@/types/productCategory'

const productCategories = ref<ProductCategory[]>([])
const loading = ref(true)
const errorMessage = ref('')
const dialogVisible = ref(false)
const saving = ref(false)
const saveErrorMessage = ref('')

const columns: AppDataTableColumn<ProductCategory>[] = [
  { field: 'name', header: 'Nombre' },
  { field: 'updatedAt', header: 'Última actualización', type: 'date' },
]

async function loadProductCategories(): Promise<void> {
  loading.value = true
  errorMessage.value = ''

  try {
    productCategories.value = await getProductCategories()
  } catch (error) {
    errorMessage.value =
      error instanceof Error ? error.message : 'No se pudieron cargar las categorías.'
  } finally {
    loading.value = false
  }
}

function openCreateDialog(): void {
  saveErrorMessage.value = ''
  dialogVisible.value = true
}

function closeCreateDialog(): void {
  if (saving.value) return

  dialogVisible.value = false
  saveErrorMessage.value = ''
}

async function saveProductCategory(name: string): Promise<void> {
  saving.value = true
  saveErrorMessage.value = ''

  try {
    const createdCategory = await createProductCategory({ name })
    productCategories.value = [createdCategory, ...productCategories.value]
    dialogVisible.value = false
  } catch (error) {
    saveErrorMessage.value =
      error instanceof Error ? error.message : 'No se pudo crear la categoría.'
  } finally {
    saving.value = false
  }
}

onMounted(loadProductCategories)
</script>

<template>
  <section>
    <div class="page-header">
      <div>
        <h2>Categorías de productos</h2>
        <p>Consulta de las categorías asociadas al catálogo de productos.</p>
      </div>

      <Button label="Nueva categoría" icon="pi pi-plus" @click="openCreateDialog" />
    </div>

    <p v-if="errorMessage" class="error-message" role="alert">
      {{ errorMessage }}
      <Button label="Reintentar" size="small" text @click="loadProductCategories" />
    </p>

    <AppDataTable
      :items="productCategories"
      :columns="columns"
      :loading="loading"
      empty-message="No hay categorías de productos registradas."
      :rows="10"
      :rows-per-page-options="[5, 10, 20, 50]"
    />

    <CategoryQuickEntryDialog
      :visible="dialogVisible"
      category-label="categoría"
      :saving="saving"
      :error-message="saveErrorMessage"
      @close="closeCreateDialog"
      @save="saveProductCategory"
    />
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

.error-message {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.75rem 1rem;
  color: #b91c1c;
  background: #fef2f2;
  border: 1px solid #fecaca;
  border-radius: 6px;
}
</style>
