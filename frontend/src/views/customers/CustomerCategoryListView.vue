<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Button from 'primevue/button'
import { createCustomerCategory, getCustomerCategories } from '@/api/customerCategoryService'
import AppDataTable from '@/components/common/AppDataTable.vue'
import CategoryQuickEntryDialog from '@/components/common/CategoryQuickEntryDialog.vue'
import type { CustomerCategory } from '@/types/customerCategory'
import type { AppDataTableColumn } from '@/types/dataTable'

const categories = ref<CustomerCategory[]>([])
const loading = ref(true)
const errorMessage = ref('')
const dialogVisible = ref(false)
const saving = ref(false)
const saveErrorMessage = ref('')

const columns: AppDataTableColumn<CustomerCategory>[] = [
  { field: 'name', header: 'Nombre' },
  { field: 'updatedAt', header: 'Última actualización', type: 'date' },
]

async function loadCategories(): Promise<void> {
  loading.value = true
  errorMessage.value = ''
  try {
    categories.value = await getCustomerCategories()
  } catch (error) {
    errorMessage.value = error instanceof Error ? error.message : 'No se pudieron cargar las categorías.'
  } finally {
    loading.value = false
  }
}

async function saveCategory(name: string): Promise<void> {
  saving.value = true
  saveErrorMessage.value = ''
  try {
    categories.value = [await createCustomerCategory(name), ...categories.value]
    dialogVisible.value = false
  } catch (error) {
    saveErrorMessage.value = error instanceof Error ? error.message : 'No se pudo crear la categoría.'
  } finally {
    saving.value = false
  }
}

onMounted(loadCategories)
</script>

<template>
  <section>
    <div class="page-header">
      <div><h2>Categorías de clientes</h2><p>Administración de las categorías opcionales para clientes.</p></div>
      <Button label="Nueva categoría" icon="pi pi-plus" @click="dialogVisible = true" />
    </div>
    <p v-if="errorMessage" class="error-message" role="alert">{{ errorMessage }} <Button label="Reintentar" size="small" text @click="loadCategories" /></p>
    <AppDataTable :items="categories" :columns="columns" :loading="loading" empty-message="No hay categorías de clientes registradas." />
    <CategoryQuickEntryDialog :visible="dialogVisible" category-label="categoría de cliente" :saving="saving" :error-message="saveErrorMessage" @close="dialogVisible = false" @save="saveCategory" />
  </section>
</template>

<style scoped>
.page-header, .error-message { display: flex; align-items: center; justify-content: space-between; }
.page-header { margin-bottom: 1.5rem; }
h2, p { margin: 0; }
.page-header p { margin-top: 0.4rem; color: #6b7280; }
.error-message { padding: 0.75rem 1rem; margin-bottom: 1rem; color: #b91c1c; background: #fef2f2; border: 1px solid #fecaca; border-radius: 6px; }
</style>
