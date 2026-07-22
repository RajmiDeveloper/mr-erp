export interface CreateStockMovementRequest {
  productId: string
  costPrice: number
  quantity: number
}

export interface StockMovementResponse {
  productId: string
  costPrice: number
  quantity: number
}
