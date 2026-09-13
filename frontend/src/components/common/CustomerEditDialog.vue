<script setup lang="ts">
import { reactive, watch } from 'vue'
import Button from 'primevue/button'
import Checkbox from 'primevue/checkbox'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'
import Select from 'primevue/select'
import type { CustomerResponse, UpdateCustomerRequest } from '@/types/customer'
import type { CustomerCategory } from '@/types/customerCategory'

const props = defineProps<{ visible: boolean; customer: CustomerResponse | null; categories: CustomerCategory[]; saving?: boolean; errorMessage?: string }>()
const emit = defineEmits<{ close: []; save: [customer: UpdateCustomerRequest] }>()
const form = reactive({ name: '', identification: '', active: true, customerCategoryId: null as string | null })

watch(() => props.customer, (customer) => {
  if (!customer) return
  form.name = customer.name
  form.identification = customer.identification ?? ''
  form.active = customer.active
  form.customerCategoryId = customer.customerCategoryId
}, { immediate: true })

function submit(): void {
  emit('save', {
    name: form.name.trim(), identification: form.identification.trim() || null,
    active: form.active, customerCategoryId: form.customerCategoryId,
  })
}
</script>

<template>
  <Dialog :visible="visible" modal header="Editar cliente" :style="{ width: 'min(92vw, 34rem)' }" :closable="!saving" :close-on-escape="!saving" @update:visible="(value) => !value && emit('close')">
    <form class="edit-form" @submit.prevent="submit">
      <p v-if="errorMessage" class="error-message" role="alert">{{ errorMessage }}</p>
      <div class="field"><label for="edit-customer-name">Nombre</label><InputText id="edit-customer-name" v-model="form.name" required maxlength="150" /></div>
      <div class="field"><label for="edit-customer-identification">Identificación</label><InputText id="edit-customer-identification" v-model="form.identification" maxlength="100" /></div>
      <div class="field"><label for="edit-customer-category">Categoría</label><Select input-id="edit-customer-category" v-model="form.customerCategoryId" :options="categories" option-label="name" option-value="id" placeholder="Sin categoría" show-clear /></div>
      <div class="active-field"><Checkbox input-id="edit-customer-active" v-model="form.active" binary /><label for="edit-customer-active">Cliente activo</label></div>
      <div class="actions"><Button type="button" label="Cancelar" severity="secondary" outlined :disabled="saving" @click="emit('close')" /><Button type="submit" label="Guardar cambios" :loading="saving" /></div>
    </form>
  </Dialog>
</template>

<style scoped>
.edit-form, .field { display: flex; flex-direction: column; }.edit-form { gap: 1rem; }.field { gap: 0.4rem; }
.active-field { display: flex; align-items: center; gap: 0.5rem; }.actions { display: flex; justify-content: flex-end; gap: 0.75rem; margin-top: 0.5rem; }
.error-message { padding: 0.75rem 1rem; margin: 0; color: #b91c1c; background: #fef2f2; border: 1px solid #fecaca; border-radius: 6px; }
</style>
