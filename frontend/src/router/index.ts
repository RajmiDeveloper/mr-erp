import { createRouter, createWebHistory } from 'vue-router'

import AppLayout from '@/components/layout/AppLayout.vue'
import DashboardView from '@/views/DashboardView.vue'
import InventoryMovementView from '@/views/inventory/InventoryMovementView.vue'
import ProductListView from '@/views/products/ProductListView.vue'
import ProductFormView from '@/views/products/ProductFormView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path: '/',
      component: AppLayout,
      children: [
        {
          path: '',
          name: 'dashboard',
          component: DashboardView,
        },
        {
          path: 'products',
          name: 'products',
          component: ProductListView,
        },
        {
          path: 'products/new',
          name: 'product-create',
          component: ProductFormView,
        },
        {
          path: 'inventory',
          name: 'inventory',
          component: InventoryMovementView,
        },
      ],
    },
  ],
})

export default router
