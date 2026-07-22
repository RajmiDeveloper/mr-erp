<script setup lang="ts">
import Button from 'primevue/button'
import InputNumber from 'primevue/inputnumber'
import { onMounted, ref } from 'vue'
import { getProducts } from '@/api/productService'
import { createStockMovement } from '@/api/stockMovementService'
import ProductSelector from '@/components/common/ProductSelector.vue'
import type { ProductResponse } from '@/types/product'

interface InventoryRow {
  id: number
  productId: string | null
  quantity: number | null
  cost: number | null
}

const products = ref<ProductResponse[]>([])
const loadingProducts = ref(true)
const productsError = ref('')
const registerError = ref('')
const successMessage = ref('')
const registering = ref(false)
const nextRowId = ref(2)
const rows = ref<InventoryRow[]>([
  { id: 1, productId: null, quantity: null, cost: null },
])

function addRow(): void {
  rows.value.push({
    id: nextRowId.value,
    productId: null,
    quantity: null,
    cost: null,
  })
  nextRowId.value += 1
}

function removeRow(rowId: number): void {
  if (rows.value.length === 1) return

  rows.value = rows.value.filter((row) => row.id !== rowId)
}

function setProductDefaults(row: InventoryRow, product: ProductResponse | null): void {
  row.quantity = 1
  row.cost = product?.costPrice ?? null
}

function createEmptyRow(): InventoryRow {
  const row = {
    id: nextRowId.value,
    productId: null,
    quantity: null,
    cost: null,
  }
  nextRowId.value += 1
  return row
}

async function registerMovements(): Promise<void> {
  registerError.value = ''
  successMessage.value = ''

  const hasInvalidRow = rows.value.some(
    (row) =>
      row.productId === null ||
      row.quantity === null ||
      row.quantity === 0 ||
      row.cost === null,
  )

  if (hasInvalidRow) {
    registerError.value =
      'Completá el producto, una cantidad distinta de cero y el costo en todas las filas.'
    return
  }

  registering.value = true

  try {
    for (const row of rows.value) {
      if (row.productId === null || row.quantity === null || row.cost === null) continue

      await createStockMovement({
        productId: row.productId,
        quantity: row.quantity,
        costPrice: row.cost,
      })
    }

    rows.value = [createEmptyRow()]
    successMessage.value = 'Los movimientos se registraron correctamente.'
    await loadProducts()
  } catch (error) {
    registerError.value =
      error instanceof Error ? error.message : 'No se pudieron registrar los movimientos.'
  } finally {
    registering.value = false
  }
}

async function loadProducts(): Promise<void> {
  loadingProducts.value = true
  productsError.value = ''

  try {
    const productList = await getProducts()
    products.value = productList.filter((product) => product.active)
  } catch (error) {
    productsError.value =
      error instanceof Error ? error.message : 'No se pudieron cargar los productos.'
  } finally {
    loadingProducts.value = false
  }
}

onMounted(loadProducts)
</script>

<template>
  <section class="inventory-page">
    <div class="page-header">
      <div>
        <h2>Movimientos de inventario</h2>
        <p>Cargá productos y cantidades positivas o negativas para ajustar el inventario.</p>
      </div>
    </div>

    <p v-if="productsError" class="error-message" role="alert">
      {{ productsError }}
      <Button label="Reintentar" size="small" text @click="loadProducts" />
    </p>

    <p v-if="registerError" class="error-message" role="alert">
      {{ registerError }}
    </p>

    <p v-if="successMessage" class="success-message" role="status">
      {{ successMessage }}
    </p>

    <div class="table-card">
      <table>
        <thead>
          <tr>
            <th scope="col">Producto</th>
            <th scope="col">Cantidad</th>
            <th scope="col">Costo unitario</th>
            <th scope="col" class="actions-column">
              <span class="sr-only">Acciones</span>
            </th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="row in rows" :key="row.id">
            <td>
              <ProductSelector
                v-model="row.productId"
                :products="products"
                :loading="loadingProducts"
                :disabled="loadingProducts || Boolean(productsError)"
                @select="setProductDefaults(row, $event)"
              />
            </td>
            <td>
              <InputNumber
                v-model="row.quantity"
                :min-fraction-digits="0"
                :max-fraction-digits="0"
                class="number-input"
              />
            </td>
            <td>
              <InputNumber
                v-model="row.cost"
                mode="currency"
                currency="ARS"
                locale="es-AR"
                placeholder="$ 0,00"
                :min="0"
                class="number-input"
              />
            </td>
            <td class="row-actions">
              <Button
                icon="pi pi-trash"
                severity="secondary"
                text
                rounded
                aria-label="Quitar fila"
                :disabled="rows.length === 1"
                @click="removeRow(row.id)"
              />
            </td>
          </tr>
        </tbody>
      </table>

      <div class="table-footer">
        <Button
          label="Agregar fila"
          icon="pi pi-plus"
          severity="secondary"
          outlined
          @click="addRow"
        />
        <Button
          label="Registrar movimientos"
          icon="pi pi-check"
          :loading="registering"
          :disabled="registering || loadingProducts || Boolean(productsError)"
          @click="registerMovements"
        />
      </div>
    </div>

  </section>
</template>

<style scoped>
.inventory-page {
  max-width: 1100px;
}

.page-header {
  display: flex;
  gap: 1rem;
  align-items: flex-start;
  justify-content: space-between;
  margin-bottom: 1.5rem;
}

h2,
p {
  margin: 0;
}

h2 {
  font-size: 1.5rem;
}

.page-header p {
  max-width: 680px;
  margin-top: 0.4rem;
  color: #6b7280;
}

.error-message {
  display: flex;
  gap: 0.75rem;
  align-items: center;
  justify-content: space-between;
  padding: 0.75rem 1rem;
  margin-bottom: 1rem;
  color: #b91c1c;
  background: #fef2f2;
  border: 1px solid #fecaca;
  border-radius: 6px;
}

.success-message {
  padding: 0.75rem 1rem;
  margin-bottom: 1rem;
  color: #065f46;
  background: #ecfdf5;
  border: 1px solid #a7f3d0;
  border-radius: 6px;
}

.table-card {
  overflow: hidden;
  background: #fff;
  border-radius: 8px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 1rem;
  text-align: left;
  border-bottom: 1px solid #e5e7eb;
}

th {
  color: #f9fafb;
  font-size: 0.85rem;
  font-weight: 600;
  background: #374151;
}

td {
  vertical-align: middle;
}

th:first-child {
  width: 46%;
}

.actions-column,
.row-actions {
  width: 1%;
  white-space: nowrap;
}

.number-input {
  width: 100%;
}

.table-footer {
  display: flex;
  gap: 0.75rem;
  justify-content: space-between;
  padding: 1rem;
}

.sr-only {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border: 0;
}

@media (max-width: 700px) {
  .page-header,
  .table-footer {
    flex-direction: column;
  }

  .table-card {
    overflow-x: auto;
  }

  table {
    min-width: 700px;
  }

  .table-footer {
    min-width: 700px;
  }
}
</style>
