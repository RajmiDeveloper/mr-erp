import { http } from './http'
import type { ProductCategory } from '@/types/productCategory'

export interface CreateProductCategoryRequest {
  name: string
}

export function getProductCategories(): Promise<ProductCategory[]> {
  return http<ProductCategory[]>('/api/product_categories')
}

export function createProductCategory(
  category: CreateProductCategoryRequest,
): Promise<ProductCategory> {
  return http<ProductCategory>('/api/product_categories', {
    method: 'POST',
    body: JSON.stringify(category),
  })
}
