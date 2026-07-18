<script setup lang="ts">
import Button from 'primevue/button'
import InputNumber from 'primevue/inputnumber'
import InputText from 'primevue/inputtext'
import Textarea from 'primevue/textarea'
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { createProduct } from '@/api/productService'

const router = useRouter()
const saving = ref(false)
const errorMessage = ref('')

const product = reactive({
  code: '',
  name: '',
  description: '',
  costPrice: null as number | null,
  salePrice: null as number | null,
})

async function saveProduct(): Promise<void> {
  errorMessage.value = ''

  if (product.costPrice === null || product.salePrice === null) {
    errorMessage.value = 'Completá los precios de costo y venta.'
    return
  }

  saving.value = true

  try {
    await createProduct({
      code: product.code.trim(),
      name: product.name.trim(),
      description: product.description.trim() || null,
      costPrice: product.costPrice,
      salePrice: product.salePrice,
    })
    await router.push({ name: 'products' })
  } catch (error) {
    errorMessage.value =
      error instanceof Error ? error.message : 'No se pudo guardar el producto.'
  } finally {
    saving.value = false
  }
}
</script>

<template>
  <section class="form-page">
    <h2>Nuevo producto</h2>

    <form class="product-form" @submit.prevent="saveProduct">
      <p v-if="errorMessage" class="error-message" role="alert">
        {{ errorMessage }}
      </p>

      <div class="field">
        <label for="code">Código</label>
        <InputText id="code" v-model="product.code" required maxlength="50" />
      </div>

      <div class="field">
        <label for="name">Nombre</label>
        <InputText id="name" v-model="product.name" required maxlength="150" />
      </div>

      <div class="field">
        <label for="description">Descripción</label>
        <Textarea
          id="description"
          v-model="product.description"
          rows="4"
          maxlength="500"
        />
      </div>

      <div class="field">
        <label for="costPrice">Precio de costo</label>
        <InputNumber
          input-id="costPrice"
          v-model="product.costPrice"
          mode="currency"
          currency="ARS"
          locale="es-AR"
          :min="0"
        />
      </div>

      <div class="field">
        <label for="salePrice">Precio de venta</label>
        <InputNumber
          input-id="salePrice"
          v-model="product.salePrice"
          mode="currency"
          currency="ARS"
          locale="es-AR"
          :min="0"
        />
      </div>

      <div class="actions">
        <RouterLink to="/products">
          <Button
            type="button"
            label="Cancelar"
            severity="secondary"
            outlined
          />
        </RouterLink>

        <Button
          type="submit"
          label="Guardar producto"
          :loading="saving"
          :disabled="saving"
        />
      </div>
    </form>
  </section>
</template>

<style scoped>
.form-page {
  max-width: 700px;
}

.product-form {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
  padding: 1.5rem;
  margin-top: 1.5rem;
  background: white;
  border-radius: 8px;
}

.field {
  display: flex;
  flex-direction: column;
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
  gap: 0.75rem;
  justify-content: flex-end;
}

.actions a {
  text-decoration: none;
}
</style>
