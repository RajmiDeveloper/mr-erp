import { http } from './http'
import type { CustomerCategory } from '@/types/customerCategory'

export function getCustomerCategories(): Promise<CustomerCategory[]> {
  return http<CustomerCategory[]>('/api/customer_categories')
}

export function createCustomerCategory(name: string): Promise<CustomerCategory> {
  return http<CustomerCategory>('/api/customer_categories', {
    method: 'POST',
    body: JSON.stringify({ name }),
  })
}
