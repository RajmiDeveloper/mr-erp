<script setup lang="ts">
import Button from 'primevue/button'
import InputNumber from 'primevue/inputnumber'
import InputText from 'primevue/inputtext'
import { computed, onMounted, ref } from 'vue'
import { getProducts } from '@/api/productService'
import CustomerQuickEntryDialog from '@/components/common/CustomerQuickEntryDialog.vue'
import ProductSelector from '@/components/common/ProductSelector.vue'
import type { ProductResponse } from '@/types/product'

interface SaleRow {
  id: number
  productId: string | null
  quantity: number | null
  price: number | null
}

const products = ref<ProductResponse[]>([])
const loadingProducts = ref(true)
const productsError = ref('')
const nextRowId = ref(2)
const rows = ref<SaleRow[]>([{ id: 1, productId: null, quantity: null, price: null }])
const customerSearch = ref('')
const selectedCustomer = ref<string | null>(null)
const customerEntryVisible = ref(false)

// Opciones temporales hasta que exista el catálogo de clientes.
const placeholderCustomers = ['Cliente de ejemplo', 'Consumidor final', 'Comercio de prueba']
const matchingCustomers = computed(() => {
  const query = customerSearch.value.trim().toLocaleLowerCase()
  if (!query) return placeholderCustomers
  return placeholderCustomers.filter((customer) => customer.toLocaleLowerCase().includes(query))
})

function addRow(): void {
  rows.value.push({ id: nextRowId.value, productId: null, quantity: null, price: null })
  nextRowId.value += 1
}

function removeRow(rowId: number): void {
  if (rows.value.length === 1) return
  rows.value = rows.value.filter((row) => row.id !== rowId)
}

function setProductDefaults(row: SaleRow, product: ProductResponse | null): void {
  row.quantity = 1
  row.price = product?.salePrice ?? null
}

function formatMoney(value: number | null): string {
  if (value === null) return '—'
  return new Intl.NumberFormat('es-AR', { style: 'currency', currency: 'ARS' }).format(value)
}

function rowTotal(row: SaleRow): number | null {
  if (row.quantity === null || row.price === null) return null
  return row.quantity * row.price
}

function total(): number {
  return rows.value.reduce((sum, row) => sum + (rowTotal(row) ?? 0), 0)
}

function selectCustomer(customer: string): void {
  selectedCustomer.value = customer
  customerSearch.value = customer
}

function useNewCustomer(customerName: string): void {
  selectedCustomer.value = customerName
  customerSearch.value = customerName
}

async function loadProducts(): Promise<void> {
  loadingProducts.value = true
  productsError.value = ''
  try {
    products.value = (await getProducts()).filter((product) => product.active)
  } catch (error) {
    productsError.value = error instanceof Error ? error.message : 'No se pudieron cargar los productos.'
  } finally {
    loadingProducts.value = false
  }
}

onMounted(loadProducts)
</script>

<template>
  <section class="sales-page">
    <div class="page-header">
      <div>
        <h2>Nueva venta</h2>
        <p>Seleccioná los productos y las cantidades que querés vender.</p>
      </div>
    </div>

    <p v-if="productsError" class="error-message" role="alert">
      {{ productsError }}
      <Button label="Reintentar" size="small" text @click="loadProducts" />
    </p>

    <div class="customer-field">
      <label for="customer-search">Cliente</label>
      <div class="customer-search-wrapper">
        <InputText
          id="customer-search"
          v-model="customerSearch"
          class="customer-search"
          placeholder="Buscá un cliente por nombre o documento"
          autocomplete="off"
          aria-describedby="customer-help"
        />
        <Button
          icon="pi pi-plus"
          text
          rounded
          type="button"
          class="customer-add-button"
          aria-label="Crear cliente"
          title="Crear cliente"
          @click="customerEntryVisible = true"
        />
      </div>
      <small id="customer-help" class="field-help">Los clientes son opciones de ejemplo hasta conectar el catálogo.</small>
      <ul v-if="customerSearch && matchingCustomers.length" class="customer-options" role="listbox" aria-label="Clientes coincidentes">
        <li v-for="customer in matchingCustomers" :key="customer" role="option" :aria-selected="selectedCustomer === customer" @click="selectCustomer(customer)">
          <i class="pi pi-user" aria-hidden="true"></i>
          <span>{{ customer }}</span>
        </li>
      </ul>
      <p v-else-if="customerSearch" class="no-customer-results">No hay clientes que coincidan.</p>
    </div>

    <CustomerQuickEntryDialog
      :visible="customerEntryVisible"
      @close="customerEntryVisible = false"
      @created="useNewCustomer"
    />

    <div class="table-card">
      <table>
        <thead>
          <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio unitario</th>
            <th>Total</th>
            <th class="actions-column"><span class="sr-only">Acciones</span></th>
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
                placeholder="Seleccioná un producto"
                @select="setProductDefaults(row, $event)"
              />
            </td>
            <td>
              <InputNumber v-model="row.quantity" :min="1" :min-fraction-digits="0" :max-fraction-digits="0" class="quantity-input-wrapper" input-class="quantity-input" />
            </td>
            <td>
              <InputNumber v-model="row.price" mode="currency" currency="ARS" locale="es-AR" :min="0" class="number-input" />
            </td>
            <td class="total-cost">{{ formatMoney(rowTotal(row)) }}</td>
            <td class="row-actions">
              <Button icon="pi pi-trash" severity="secondary" text rounded aria-label="Quitar fila" :disabled="rows.length === 1" @click="removeRow(row.id)" />
            </td>
          </tr>
        </tbody>
      </table>

      <div class="table-summary">
        <span>Total de la venta</span>
        <strong>{{ formatMoney(total()) }}</strong>
      </div>
      <div class="table-footer">
        <Button label="Agregar producto" icon="pi pi-plus" severity="secondary" outlined @click="addRow" />
        <Button label="Registrar venta" icon="pi pi-check" disabled title="El registro de ventas todavía no está disponible" />
      </div>
    </div>
  </section>
</template>

<style scoped>
.sales-page { max-width: 1100px; }
.page-header { margin-bottom: 1.5rem; }
h2, p { margin: 0; }
h2 { font-size: 1.5rem; }
.page-header p { margin-top: 0.4rem; color: #6b7280; }
.error-message { display: flex; gap: 0.75rem; align-items: center; justify-content: space-between; padding: 0.75rem 1rem; margin-bottom: 1rem; color: #b91c1c; background: #fef2f2; border: 1px solid #fecaca; border-radius: 6px; }
.customer-field { position: relative; max-width: 560px; margin-bottom: 1.25rem; }
.customer-field label { display: block; margin-bottom: 0.45rem; font-weight: 600; }
.customer-search-wrapper { position: relative; }
.customer-search { width: 100%; padding-right: 2.5rem; }
.customer-add-button { position: absolute; top: 50%; right: 0.2rem; width: 2.2rem; height: 2.2rem; transform: translateY(-50%); }
.field-help { display: block; margin-top: 0.35rem; color: #6b7280; }
.customer-options { position: absolute; z-index: 2; width: 100%; padding: 0.35rem 0; margin: 0.25rem 0 0; overflow: hidden; list-style: none; background: #fff; border: 1px solid #d1d5db; border-radius: 6px; box-shadow: 0 5px 12px rgb(0 0 0 / 12%); }
.customer-options li { display: flex; gap: 0.65rem; align-items: center; padding: 0.7rem 0.85rem; cursor: pointer; }
.customer-options li:hover { background: #f3f4f6; }
.customer-options i { color: #6b7280; }
.no-customer-results { padding: 0.7rem 0.85rem; margin: 0.25rem 0 0; color: #6b7280; background: #fff; border: 1px solid #e5e7eb; border-radius: 6px; }
.table-card { overflow: hidden; background: #fff; border-radius: 8px; }
table { width: 100%; border-collapse: collapse; }
th, td { padding: 1rem; text-align: left; border-bottom: 1px solid #e5e7eb; }
th { color: #f9fafb; font-size: 0.85rem; font-weight: 600; background: #374151; }
th:first-child { width: 38%; }
td { vertical-align: middle; }
.total-cost { font-weight: 600; white-space: nowrap; }
.actions-column, .row-actions { width: 1%; white-space: nowrap; }
.number-input { width: 100%; }
:deep(.quantity-input-wrapper) { width: auto !important; }
:deep(.quantity-input) { width: 8rem !important; }
.table-summary { display: flex; justify-content: flex-end; gap: 1rem; padding: 1rem; font-size: 1.05rem; }
.table-summary strong { min-width: 10rem; text-align: right; }
.table-footer { display: flex; justify-content: space-between; gap: 0.75rem; padding: 1rem; }
.sr-only { position: absolute; width: 1px; height: 1px; padding: 0; margin: -1px; overflow: hidden; clip: rect(0, 0, 0, 0); white-space: nowrap; border: 0; }
@media (max-width: 700px) { .table-card { overflow-x: auto; } table { min-width: 700px; } .table-footer { min-width: 700px; } }
</style>
