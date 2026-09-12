export interface CreateProductRequest {
  code: string
  name: string
  description: string | null
  costPrice: number
  salePrice: number
  productCategoryId: string | null
}

export interface UpdateProductRequest {
  name: string
  description: string | null
  costPrice: number
  salePrice: number
  currentStock: number
  active: boolean
  productCategoryId: string | null
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
  productCategoryId: string | null
  productCategoryName: string | null
}
