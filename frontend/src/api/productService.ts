import { http } from './http'
import type {
  CreateProductRequest,
  ProductResponse,
  UpdateProductRequest,
} from '@/types/product'

export function getProducts(): Promise<ProductResponse[]> {
  return http<ProductResponse[]>('/api/products')
}

export function createProduct(product: CreateProductRequest): Promise<ProductResponse> {
  return http<ProductResponse>('/api/products', {
    method: 'POST',
    body: JSON.stringify(product),
  })
}

export function updateProduct(
  id: string,
  product: UpdateProductRequest,
): Promise<ProductResponse> {
  return http<ProductResponse>(`/api/products/${id}`, {
    method: 'PUT',
    body: JSON.stringify(product),
  })
}
