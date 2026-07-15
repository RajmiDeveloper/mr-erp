<script setup lang="ts">
import AppHeader from './AppHeader.vue'
import AppSidebar from './AppSidebar.vue'
</script>

<template>
  <div class="app-layout">
    <AppSidebar />

    <div class="app-content">
      <AppHeader />

      <main class="page-content">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<style scoped>
.app-layout {
  display: flex;
  min-height: 100vh;
}

.app-content {
  display: flex;
  flex: 1;
  flex-direction: column;
  min-width: 0;
}

.page-content {
  flex: 1;
  padding: 1.5rem;
  background: var(--p-surface-50);
}
</style>
