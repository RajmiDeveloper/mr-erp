<script setup lang="ts">
import { reactive, watch } from 'vue'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'

const props = defineProps<{
  visible: boolean
  saving?: boolean
  errorMessage?: string
}>()

const emit = defineEmits<{
  close: []
  save: [name: string]
}>()

const form = reactive({ name: '' })
const validationMessage = reactive({ value: '' })

watch(
  () => props.visible,
  (visible) => {
    if (visible) {
      form.name = ''
      validationMessage.value = ''
    }
  },
)

function submit(): void {
  const name = form.name.trim()

  if (!name) {
    validationMessage.value = 'Completá el nombre de la categoría.'
    return
  }

  emit('save', name)
}
</script>

<template>
  <Dialog
    :visible="visible"
    modal
    header="Nueva categoría"
    :style="{ width: 'min(92vw, 34rem)' }"
    :closable="!saving"
    :close-on-escape="!saving"
    @update:visible="(value) => !value && emit('close')"
  >
    <form class="quick-entry-form" @submit.prevent="submit">
      <p v-if="errorMessage || validationMessage.value" class="error-message" role="alert">
        {{ errorMessage || validationMessage.value }}
      </p>

      <div class="field">
        <label for="product-category-name">Nombre</label>
        <InputText
          id="product-category-name"
          v-model="form.name"
          placeholder="Ej.: Bebidas"
          maxlength="50"
          autofocus
          :disabled="saving"
        />
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
        <Button type="submit" label="Guardar categoría" :loading="saving" />
      </div>
    </form>
  </Dialog>
</template>

<style scoped>
.quick-entry-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
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
