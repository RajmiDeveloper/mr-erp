<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Button from 'primevue/button'
import { getCustomerCategories } from '@/api/customerCategoryService'
import { getCustomers, updateCustomer } from '@/api/customerService'
import AppDataTable from '@/components/common/AppDataTable.vue'
import CustomerEditDialog from '@/components/common/CustomerEditDialog.vue'
import StatusIndicator from '@/components/common/StatusIndicator.vue'
import type { CustomerResponse, UpdateCustomerRequest } from '@/types/customer'
import type { CustomerCategory } from '@/types/customerCategory'
import type { AppDataTableColumn } from '@/types/dataTable'

const customers = ref<CustomerResponse[]>([])
const categories = ref<CustomerCategory[]>([])
const loading = ref(true)
const errorMessage = ref('')
const selectedCustomer = ref<CustomerResponse | null>(null)
const dialogVisible = ref(false)
const saving = ref(false)
const saveErrorMessage = ref('')

const columns: AppDataTableColumn<CustomerResponse>[] = [
  { field: 'code', header: 'Código' }, { field: 'name', header: 'Nombre' },
  { field: 'identification', header: 'Identificación' }, { field: 'customerCategoryName', header: 'Categoría' },
  { field: 'active', header: 'Estado', type: 'boolean', trueLabel: 'Activo', falseLabel: 'Inactivo' },
  { field: 'id', header: '', sortable: false },
]

async function loadCustomers(): Promise<void> {
  loading.value = true; errorMessage.value = ''
  try { customers.value = await getCustomers() }
  catch (error) { errorMessage.value = error instanceof Error ? error.message : 'No se pudieron cargar los clientes.' }
  finally { loading.value = false }
}

async function saveCustomer(customer: UpdateCustomerRequest): Promise<void> {
  if (!selectedCustomer.value) return
  saving.value = true; saveErrorMessage.value = ''
  try {
    const updatedCustomer = await updateCustomer(selectedCustomer.value.id, customer)
    customers.value = customers.value.map((item) => item.id === updatedCustomer.id ? updatedCustomer : item)
    dialogVisible.value = false; selectedCustomer.value = null
  } catch (error) { saveErrorMessage.value = error instanceof Error ? error.message : 'No se pudo actualizar el cliente.' }
  finally { saving.value = false }
}

onMounted(() => { void loadCustomers(); void getCustomerCategories().then((items) => { categories.value = items }) })
</script>

<template>
  <section>
    <div class="page-header"><div><h2>Clientes</h2><p>Administración del catálogo de clientes.</p></div><RouterLink to="/customers/new"><Button label="Nuevo cliente" icon="pi pi-plus" /></RouterLink></div>
    <p v-if="errorMessage" class="error-message" role="alert">{{ errorMessage }} <Button label="Reintentar" size="small" text @click="loadCustomers" /></p>
    <AppDataTable :items="customers" :columns="columns" :loading="loading" empty-message="No hay clientes registrados.">
      <template #cell-active="{ value }"><StatusIndicator :active="Boolean(value)" /></template>
      <template #cell-id="{ data }"><Button icon="pi pi-pencil" aria-label="Editar cliente" severity="secondary" rounded class="edit-button" @click="selectedCustomer = data; saveErrorMessage = ''; dialogVisible = true" /></template>
    </AppDataTable>
    <CustomerEditDialog :visible="dialogVisible" :customer="selectedCustomer" :categories="categories" :saving="saving" :error-message="saveErrorMessage" @close="dialogVisible = false; selectedCustomer = null" @save="saveCustomer" />
  </section>
</template>

<style scoped>
.page-header, .error-message { display: flex; align-items: center; justify-content: space-between; }.page-header { margin-bottom: 1.5rem; } h2, p { margin: 0; }.page-header p { margin-top: 0.4rem; color: #6b7280; } a { text-decoration: none; }
.error-message { padding: 0.75rem 1rem; margin-bottom: 1rem; color: #b91c1c; background: #fef2f2; border: 1px solid #fecaca; border-radius: 6px; }.edit-button { width: 2.25rem; height: 2.25rem; padding: 0; }
</style>
