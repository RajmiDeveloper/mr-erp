<script setup lang="ts">
import { reactive, watch } from 'vue'
import Button from 'primevue/button'
import Checkbox from 'primevue/checkbox'
import Dialog from 'primevue/dialog'
import InputNumber from 'primevue/inputnumber'
import InputText from 'primevue/inputtext'
import Textarea from 'primevue/textarea'
import type { ProductResponse, UpdateProductRequest } from '@/types/product'

const props = defineProps<{
  visible: boolean
  product: ProductResponse | null
  saving?: boolean
  errorMessage?: string
}>()

const emit = defineEmits<{
  close: []
  save: [product: UpdateProductRequest]
}>()

const form = reactive({
  name: '',
  description: '',
  costPrice: null as number | null,
  salePrice: null as number | null,
  currentStock: null as number | null,
  active: true,
})

watch(
  () => props.product,
  (product) => {
    if (!product) return

    form.name = product.name
    form.description = product.description ?? ''
    form.costPrice = product.costPrice
    form.salePrice = product.salePrice
    form.currentStock = product.currentStock
    form.active = product.active
  },
  { immediate: true },
)

function submit(): void {
  if (
    form.costPrice === null ||
    form.salePrice === null ||
    form.currentStock === null
  ) {
    return
  }

  emit('save', {
    name: form.name.trim(),
    description: form.description.trim() || null,
    costPrice: form.costPrice,
    salePrice: form.salePrice,
    currentStock: form.currentStock,
    active: form.active,
  })
}
</script>

<template>
  <Dialog
    :visible="visible"
    modal
    header="Editar producto"
    :style="{ width: 'min(92vw, 34rem)' }"
    :closable="!saving"
    :close-on-escape="!saving"
    @update:visible="(value) => !value && emit('close')"
  >
    <form class="edit-form" @submit.prevent="submit">
      <p v-if="errorMessage" class="error-message" role="alert">
        {{ errorMessage }}
      </p>

      <div class="field">
        <label for="edit-name">Nombre</label>
        <InputText id="edit-name" v-model="form.name" required maxlength="150" />
      </div>

      <div class="field">
        <label for="edit-description">Descripción</label>
        <Textarea
          id="edit-description"
          v-model="form.description"
          rows="4"
          maxlength="500"
        />
      </div>

      <div class="field">
        <label for="edit-cost-price">Precio de costo</label>
        <InputNumber
          input-id="edit-cost-price"
          class="price-input"
          v-model="form.costPrice"
          mode="currency"
          currency="ARS"
          locale="es-AR"
          :min="0"
        />
      </div>

      <div class="field">
        <label for="edit-sale-price">Precio de venta</label>
        <InputNumber
          input-id="edit-sale-price"
          class="price-input"
          v-model="form.salePrice"
          mode="currency"
          currency="ARS"
          locale="es-AR"
          :min="0"
        />
      </div>

      <div class="field">
        <label for="edit-current-stock">Stock actual</label>
        <InputNumber
          input-id="edit-current-stock"
          class="stock-input"
          v-model="form.currentStock"
          :min="0"
          :use-grouping="false"
        />
      </div>

      <div class="active-field">
        <Checkbox input-id="edit-active" v-model="form.active" binary />
        <label for="edit-active">Producto activo</label>
      </div>

      <div class="actions">
        <Button
          type="button"
          label="Cancelar"
          severity="secondary"
          outlined
          :disabled="saving"
          @click="emit('close')"
        />
        <Button type="submit" label="Guardar cambios" :loading="saving" />
      </div>
    </form>
  </Dialog>
</template>

<style scoped>
.edit-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
}

.price-input {
  width: 17rem;
  max-width: 100%;
}

:deep(.price-input .p-inputnumber-input) {
  width: 100%;
}

.stock-input {
  width: 8rem;
  max-width: 100%;
}

:deep(.stock-input .p-inputnumber-input) {
  width: 100%;
}

.active-field {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.error-message {
  padding: 0.75rem 1rem;
  margin: 0;
  color: #b91c1c;
  background: #fef2f2;
  border: 1px solid #fecaca;
  border-radius: 6px;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  margin-top: 0.5rem;
}
</style>
