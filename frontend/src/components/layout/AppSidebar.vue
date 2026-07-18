<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink } from 'vue-router'

const isCollapsed = ref(false)
</script>

<template>
  <aside class="sidebar" :class="{ collapsed: isCollapsed }">
    <div class="sidebar-header">
      <h1 class="logo">MR ERP</h1>

      <button
        class="toggle-button"
        type="button"
        :aria-label="isCollapsed ? 'Expandir barra lateral' : 'Contraer barra lateral'"
        :title="isCollapsed ? 'Expandir barra lateral' : 'Contraer barra lateral'"
        @click="isCollapsed = !isCollapsed"
      >
        <i :class="isCollapsed ? 'pi pi-angle-right' : 'pi pi-angle-left'"></i>
      </button>
    </div>

    <nav class="navigation">
      <RouterLink to="/">
        <i class="pi pi-home"></i>
        <span>Inicio</span>
      </RouterLink>

      <RouterLink to="/products">
        <i class="pi pi-box"></i>
        <span>Productos</span>
      </RouterLink>
    </nav>
  </aside>
</template>

<style scoped>
.sidebar {
  position: relative;
  width: 240px;
  padding: 1.5rem 1rem;
  color: white;
  background: #1f2937;
  transition: width 0.2s ease;
}

.sidebar.collapsed {
  width: 72px;
  padding-right: 0.75rem;
  padding-left: 0.75rem;
}

.sidebar-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 2rem;
}

.logo {
  margin: 0;
  font-size: 1.4rem;
  white-space: nowrap;
  overflow: hidden;
}

.toggle-button {
  display: grid;
  flex: 0 0 auto;
  width: 2rem;
  height: 2rem;
  padding: 0;
  color: #d1d5db;
  cursor: pointer;
  background: transparent;
  border: 0;
  border-radius: 6px;
  place-items: center;
}

.toggle-button:hover {
  color: white;
  background: #374151;
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

.navigation a span {
  white-space: nowrap;
  overflow: hidden;
  transition: opacity 0.15s ease;
}

.sidebar.collapsed .logo,
.sidebar.collapsed .navigation a span {
  width: 0;
  opacity: 0;
}

.sidebar.collapsed .sidebar-header {
  justify-content: center;
}

.sidebar.collapsed .navigation a {
  justify-content: center;
  padding-right: 0.8rem;
  padding-left: 0.8rem;
}

.navigation a:hover,
.navigation a.router-link-exact-active {
  color: white;
  background: #374151;
}
</style>
