<script setup lang="ts">
import Select from 'primevue/select'
import type { ProductResponse } from '@/types/product'

const props = withDefaults(
  defineProps<{
    modelValue: string | null
    products: ProductResponse[]
    loading?: boolean
    disabled?: boolean
    placeholder?: string
  }>(),
  {
    loading: false,
    disabled: false,
    placeholder: 'Seleccioná un producto',
  },
)

const emit = defineEmits<{
  'update:modelValue': [productId: string | null]
  select: [product: ProductResponse | null]
}>()

function selectProduct(productId: string | null): void {
  emit('update:modelValue', productId)
  emit(
    'select',
    props.products.find((product) => product.id === productId) ?? null,
  )
}
</script>

<template>
  <Select
    :model-value="modelValue"
    :options="products"
    option-label="name"
    option-value="id"
    :placeholder="placeholder"
    filter
    :loading="loading"
    :disabled="disabled"
    class="product-selector"
    @update:model-value="selectProduct"
  >
    <template #option="{ option }">
      <span>{{ option.code }} · {{ option.name }}</span>
    </template>
  </Select>
</template>

<style scoped>
.product-selector {
  width: 100%;
}
</style>
