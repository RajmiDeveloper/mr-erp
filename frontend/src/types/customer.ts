export interface CreateCustomerRequest {
  code: string
  name: string
  identification: string | null
  customerCategoryId: string | null
}

export interface UpdateCustomerRequest {
  name: string
  identification: string | null
  active: boolean
  customerCategoryId: string | null
}

export interface CustomerResponse {
  id: string
  code: string
  name: string
  identification: string | null
  active: boolean
  customerCategoryId: string | null
  customerCategoryName: string | null
}
