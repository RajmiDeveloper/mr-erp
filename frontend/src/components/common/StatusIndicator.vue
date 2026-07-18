<script setup lang="ts">
withDefaults(
  defineProps<{
    active: boolean
    activeLabel?: string
    inactiveLabel?: string
  }>(),
  {
    activeLabel: 'Activo',
    inactiveLabel: 'Inactivo',
  },
)
</script>

<template>
  <span class="status-indicator" :class="{ 'status-indicator--inactive': !active }">
    <span class="status-indicator__dot" aria-hidden="true" />
    <span>{{ active ? activeLabel : inactiveLabel }}</span>
  </span>
</template>

<style scoped>
.status-indicator {
  --status-color: #3de598;
  --status-dot-color: #159b62;

  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  min-width: 6.5rem;
  padding: 0.35rem 0.75rem;
  color: #064e3b;
  background: var(--status-color);
  border-radius: 9999px;
  font-size: 0.875rem;
  font-weight: 600;
  line-height: 1.25rem;
}

.status-indicator--inactive {
  --status-color: #e5443d;
  --status-dot-color: #9f2420;

  color: #fff7f7;
}

.status-indicator__dot {
  width: 0.5rem;
  height: 0.5rem;
  flex: 0 0 0.5rem;
  background: var(--status-dot-color);
  border-radius: 50%;
}
</style>
