<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import Select from 'primevue/select'
import { createCustomer } from '@/api/customerService'
import { getCustomerCategories } from '@/api/customerCategoryService'
import type { CustomerCategory } from '@/types/customerCategory'

const router = useRouter()
const saving = ref(false)
const errorMessage = ref('')
const categories = ref<CustomerCategory[]>([])
const categoriesLoading = ref(false)
const customer = reactive({ code: '', name: '', identification: '', customerCategoryId: null as string | null })

async function loadCategories(): Promise<void> {
  categoriesLoading.value = true
  try { categories.value = await getCustomerCategories() } finally { categoriesLoading.value = false }
}

async function saveCustomer(): Promise<void> {
  errorMessage.value = ''
  saving.value = true
  try {
    await createCustomer({
      code: customer.code.trim(), name: customer.name.trim(),
      identification: customer.identification.trim() || null,
      customerCategoryId: customer.customerCategoryId,
    })
    await router.push({ name: 'customers' })
  } catch (error) {
    errorMessage.value = error instanceof Error ? error.message : 'No se pudo guardar el cliente.'
  } finally { saving.value = false }
}

onMounted(loadCategories)
</script>

<template>
  <section class="form-page">
    <h2>Nuevo cliente</h2>
    <form class="customer-form" @submit.prevent="saveCustomer">
      <p v-if="errorMessage" class="error-message" role="alert">{{ errorMessage }}</p>
      <div class="field"><label for="customer-code">Código</label><InputText id="customer-code" v-model="customer.code" required maxlength="50" /></div>
      <div class="field"><label for="customer-name">Nombre</label><InputText id="customer-name" v-model="customer.name" required maxlength="150" /></div>
      <div class="field"><label for="customer-identification">Identificación</label><InputText id="customer-identification" v-model="customer.identification" maxlength="100" /></div>
      <div class="field"><label for="customer-category">Categoría</label><Select input-id="customer-category" v-model="customer.customerCategoryId" :options="categories" option-label="name" option-value="id" placeholder="Sin categoría" :loading="categoriesLoading" show-clear /></div>
      <div class="actions"><RouterLink to="/customers"><Button type="button" label="Cancelar" severity="secondary" outlined /></RouterLink><Button type="submit" label="Guardar cliente" :loading="saving" :disabled="saving" /></div>
    </form>
  </section>
</template>

<style scoped>
.form-page { max-width: 700px; }
.customer-form { display: flex; flex-direction: column; gap: 1.25rem; padding: 1.5rem; margin-top: 1.5rem; background: white; border-radius: 8px; }
.field { display: flex; flex-direction: column; gap: 0.5rem; }
.error-message { padding: 0.75rem 1rem; margin: 0; color: #b91c1c; background: #fef2f2; border: 1px solid #fecaca; border-radius: 6px; }
.actions { display: flex; gap: 0.75rem; justify-content: flex-end; }.actions a { text-decoration: none; }
</style>
