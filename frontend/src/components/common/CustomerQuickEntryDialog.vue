<script setup lang="ts">
import { reactive, ref } from 'vue'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'

defineProps<{
  visible: boolean
}>()

const emit = defineEmits<{
  close: []
  created: [customerName: string]
}>()

const form = reactive({
  name: '',
  document: '',
  phone: '',
})
const errorMessage = ref('')

function resetForm(): void {
  form.name = ''
  form.document = ''
  form.phone = ''
  errorMessage.value = ''
}

function close(): void {
  resetForm()
  emit('close')
}

function save(): void {
  const customerName = form.name.trim()
  if (!customerName) {
    errorMessage.value = 'Completá el nombre del cliente.'
    return
  }

  emit('created', customerName)
  close()
}
</script>

<template>
  <Dialog
    :visible="visible"
    modal
    header="Nuevo cliente"
    :style="{ width: 'min(92vw, 34rem)' }"
    @update:visible="(value) => !value && close()"
  >
    <form class="quick-entry-form" @submit.prevent="save">
      <p class="placeholder-message">
        Formulario de ejemplo. La creación todavía no se guarda porque el catálogo de clientes no está conectado.
      </p>

      <p v-if="errorMessage" class="error-message" role="alert">{{ errorMessage }}</p>

      <div class="field">
        <label for="quick-customer-name">Nombre o razón social</label>
        <InputText id="quick-customer-name" v-model="form.name" placeholder="Ej.: María González" maxlength="150" autofocus />
      </div>

      <div class="field">
        <label for="quick-customer-document">Documento / CUIT</label>
        <InputText id="quick-customer-document" v-model="form.document" placeholder="Ej.: 20-12345678-9" maxlength="20" />
      </div>

      <div class="field">
        <label for="quick-customer-phone">Teléfono</label>
        <InputText id="quick-customer-phone" v-model="form.phone" placeholder="Ej.: 11 5555-5555" maxlength="30" />
      </div>

      <div class="actions">
        <Button type="button" label="Cancelar" severity="secondary" outlined @click="close" />
        <Button type="submit" label="Usar cliente" />
      </div>
    </form>
  </Dialog>
</template>

<style scoped>
.quick-entry-form { display: flex; flex-direction: column; gap: 1rem; }
.placeholder-message { padding: 0.75rem 1rem; margin: 0; color: #92400e; background: #fffbeb; border: 1px solid #fde68a; border-radius: 6px; }
.error-message { padding: 0.75rem 1rem; margin: 0; color: #b91c1c; background: #fef2f2; border: 1px solid #fecaca; border-radius: 6px; }
.field { display: flex; flex-direction: column; gap: 0.4rem; }
.actions { display: flex; justify-content: flex-end; gap: 0.75rem; margin-top: 0.5rem; }
</style>
