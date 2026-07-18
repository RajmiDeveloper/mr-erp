export interface CreateProductRequest {
  code: string
  name: string
  description: string | null
  costPrice: number
  salePrice: number
}

export interface UpdateProductRequest {
  name: string
  description: string | null
  costPrice: number
  salePrice: number
  currentStock: number
  active: boolean
}

export interface ProductResponse {
  id: string
  code: string
  name: string
  description: string | null
  costPrice: number
  salePrice: number
  currentStock: number
  active: boolean
}
