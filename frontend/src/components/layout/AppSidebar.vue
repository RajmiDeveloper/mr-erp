<script setup lang="ts">
import { RouterLink } from 'vue-router'
</script>

<template>
  <aside class="sidebar">
    <h1 class="logo">MR ERP</h1>

    <nav class="navigation">
      <RouterLink to="/">
        <i class="pi pi-home"></i>
        Inicio
      </RouterLink>

      <RouterLink to="/products">
        <i class="pi pi-box"></i>
        Productos
      </RouterLink>
    </nav>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 240px;
  padding: 1.5rem 1rem;
  color: white;
  background: #1f2937;
}

.logo {
  margin: 0 0 2rem;
  font-size: 1.4rem;
}

.navigation {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.navigation a {
  display: flex;
  gap: 0.75rem;
  align-items: center;
  padding: 0.8rem 1rem;
  color: #d1d5db;
  text-decoration: none;
  border-radius: 6px;
}

.navigation a:hover,
.navigation a.router-link-active {
  color: white;
  background: #374151;
}
</style>
