<script setup lang="ts">
import Button from 'primevue/button'
import { onMounted, ref } from 'vue'
import { getProducts, updateProduct } from '@/api/productService'
import AppDataTable from '@/components/common/AppDataTable.vue'
import ProductEditDialog from '@/components/common/ProductEditDialog.vue'
import StatusIndicator from '@/components/common/StatusIndicator.vue'
import type { AppDataTableColumn } from '@/types/dataTable'
import type { ProductResponse, UpdateProductRequest } from '@/types/product'

const products = ref<ProductResponse[]>([])
const loading = ref(true)
const errorMessage = ref('')
const selectedProduct = ref<ProductResponse | null>(null)
const editDialogVisible = ref(false)
const savingEdit = ref(false)
const editErrorMessage = ref('')

const columns: AppDataTableColumn<ProductResponse>[] = [
  { field: 'code', header: 'Código' },
  { field: 'name', header: 'Nombre' },
  {
    field: 'salePrice',
    header: 'Precio de venta',
    type: 'currency',
    currency: 'ARS',
  },
  {
    field: 'active',
    header: 'Estado',
    type: 'boolean',
    trueLabel: 'Activo',
    falseLabel: 'Inactivo',
  },
  { field: 'id', header: '', sortable: false },
]

function openEditDialog(product: ProductResponse): void {
  selectedProduct.value = product
  editErrorMessage.value = ''
  editDialogVisible.value = true
}

function closeEditDialog(): void {
  if (savingEdit.value) return

  editDialogVisible.value = false
  selectedProduct.value = null
  editErrorMessage.value = ''
}

async function saveProduct(productData: UpdateProductRequest): Promise<void> {
  if (!selectedProduct.value) return

  savingEdit.value = true
  editErrorMessage.value = ''

  try {
    const updatedProduct = await updateProduct(selectedProduct.value.id, productData)
    products.value = products.value.map((product) =>
      product.id === updatedProduct.id ? updatedProduct : product,
    )
    editDialogVisible.value = false
    selectedProduct.value = null
    editErrorMessage.value = ''
  } catch (error) {
    editErrorMessage.value =
      error instanceof Error ? error.message : 'No se pudo actualizar el producto.'
  } finally {
    savingEdit.value = false
  }
}

async function loadProducts(): Promise<void> {
  loading.value = true
  errorMessage.value = ''

  try {
    products.value = await getProducts()
  } catch (error) {
    errorMessage.value =
      error instanceof Error ? error.message : 'No se pudieron cargar los productos.'
  } finally {
    loading.value = false
  }
}

onMounted(loadProducts)
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

    <p v-if="errorMessage" class="error-message" role="alert">
      {{ errorMessage }}
      <Button label="Reintentar" size="small" text @click="loadProducts" />
    </p>

    <AppDataTable
      :items="products"
      :columns="columns"
      :loading="loading"
      empty-message="No hay productos registrados."
      :rows="10"
      :rows-per-page-options="[5, 10, 20, 50]"
    >
      <template #cell-active="{ value }">
        <StatusIndicator :active="Boolean(value)" />
      </template>
      <template #cell-id="{ data }">
        <Button
          icon="pi pi-pencil"
          aria-label="Editar producto"
          severity="secondary"
          rounded
          class="edit-button"
          @click="openEditDialog(data)"
        />
      </template>
    </AppDataTable>

    <ProductEditDialog
      :visible="editDialogVisible"
      :product="selectedProduct"
      :saving="savingEdit"
      :error-message="editErrorMessage"
      @close="closeEditDialog"
      @save="saveProduct"
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

.edit-button {
  width: 2.25rem;
  height: 2.25rem;
  padding: 0;
  border-radius: 0.5rem;
}

:deep(.p-datatable-thead > tr > th:last-child),
:deep(.p-datatable-tbody > tr > td:last-child) {
  width: 1%;
  padding-right: 0.75rem;
  padding-left: 0.75rem;
  white-space: nowrap;
}
</style>
