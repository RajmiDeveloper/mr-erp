import { http } from './http'
import type { CreateCustomerRequest, CustomerResponse, UpdateCustomerRequest } from '@/types/customer'

export function getCustomers(): Promise<CustomerResponse[]> {
  return http<CustomerResponse[]>('/api/customers')
}

export function createCustomer(customer: CreateCustomerRequest): Promise<CustomerResponse> {
  return http<CustomerResponse>('/api/customers', {
    method: 'POST',
    body: JSON.stringify(customer),
  })
}

export function updateCustomer(id: string, customer: UpdateCustomerRequest): Promise<CustomerResponse> {
  return http<CustomerResponse>(`/api/customers/${id}`, {
    method: 'PUT',
    body: JSON.stringify(customer),
  })
}
