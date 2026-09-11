import { createRouter, createWebHistory } from 'vue-router'

import AppLayout from '@/components/layout/AppLayout.vue'
import DashboardView from '@/views/DashboardView.vue'
import InventoryMovementView from '@/views/inventory/InventoryMovementView.vue'
import ProductListView from '@/views/products/ProductListView.vue'
import ProductCategoryListView from '@/views/products/ProductCategoryListView.vue'
import ProductFormView from '@/views/products/ProductFormView.vue'
import SaleEntryView from '@/views/sales/SaleEntryView.vue'

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
          path: 'product-categories',
          name: 'product-categories',
          component: ProductCategoryListView,
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
        {
          path: 'sales/new',
          name: 'sale-create',
          component: SaleEntryView,
        },
      ],
    },
  ],
})

export default router
