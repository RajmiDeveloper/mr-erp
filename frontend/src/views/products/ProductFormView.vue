<script setup lang="ts">
import Button from 'primevue/button'
import InputNumber from 'primevue/inputnumber'
import InputText from 'primevue/inputtext'
import Textarea from 'primevue/textarea'
import { reactive } from 'vue'

const product = reactive({
  code: '',
  name: '',
  description: '',
  costPrice: null as number | null,
  salePrice: null as number | null,
})

function saveProduct(): void {
  console.log(product)
}
</script>

<template>
  <section class="form-page">
    <h2>Nuevo producto</h2>

    <form class="product-form" @submit.prevent="saveProduct">
      <div class="field">
        <label for="code">Código</label>
        <InputText id="code" v-model="product.code" />
      </div>

      <div class="field">
        <label for="name">Nombre</label>
        <InputText id="name" v-model="product.name" />
      </div>

      <div class="field">
        <label for="description">Descripción</label>
        <Textarea
          id="description"
          v-model="product.description"
          rows="4"
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

        <Button type="submit" label="Guardar producto" />
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

.actions {
  display: flex;
  gap: 0.75rem;
  justify-content: flex-end;
}

.actions a {
  text-decoration: none;
}
</style>
