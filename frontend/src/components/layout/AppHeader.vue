<template>
  <header class="header">
    <span>Sistema de gestión</span>

    <div class="user">
      <i class="pi pi-user"></i>
      Administrador
    </div>
  </header>
</template>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
  padding: 0 1.5rem;
  background: white;
  border-bottom: 1px solid #e5e7eb;
}

.user {
  display: flex;
  gap: 0.5rem;
  align-items: center;
}
</style>
