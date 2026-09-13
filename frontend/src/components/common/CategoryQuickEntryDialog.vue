<script setup lang="ts">
import { ref, watch } from 'vue'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'

const props = defineProps<{
  visible: boolean
  categoryLabel: string
  saving?: boolean
  errorMessage?: string
}>()

const emit = defineEmits<{
  close: []
  save: [name: string]
}>()

const name = ref('')
const validationMessage = ref('')

watch(
  () => props.visible,
  (visible) => {
    if (!visible) return
    name.value = ''
    validationMessage.value = ''
  },
)

function submit(): void {
  const normalizedName = name.value.trim()
  if (!normalizedName) {
    validationMessage.value = `Completá el nombre de la ${props.categoryLabel}.`
    return
  }

  emit('save', normalizedName)
}
</script>

<template>
  <Dialog
    :visible="visible"
    modal
    :header="`Nueva ${categoryLabel}`"
    :style="{ width: 'min(92vw, 34rem)' }"
    :closable="!saving"
    :close-on-escape="!saving"
    @update:visible="(value) => !value && emit('close')"
  >
    <form class="quick-entry-form" @submit.prevent="submit">
      <p v-if="errorMessage || validationMessage" class="error-message" role="alert">
        {{ errorMessage || validationMessage }}
      </p>

      <div class="field">
        <label for="category-name">Nombre</label>
        <InputText
          id="category-name"
          v-model="name"
          placeholder="Ej.: Minoristas"
          maxlength="50"
          autofocus
          :disabled="saving"
        />
      </div>

      <div class="actions">
        <Button type="button" label="Cancelar" severity="secondary" outlined :disabled="saving" @click="emit('close')" />
        <Button type="submit" :label="`Guardar ${categoryLabel}`" :loading="saving" />
      </div>
    </form>
  </Dialog>
</template>

<style scoped>
.quick-entry-form, .field { display: flex; flex-direction: column; }
.quick-entry-form { gap: 1rem; }
.field { gap: 0.4rem; }
.error-message { padding: 0.75rem 1rem; margin: 0; color: #b91c1c; background: #fef2f2; border: 1px solid #fecaca; border-radius: 6px; }
.actions { display: flex; justify-content: flex-end; gap: 0.75rem; margin-top: 0.5rem; }
</style>
