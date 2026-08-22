export interface CreateStockMovementRequest {
  productId: string
  costPrice: number
  quantity: number
}

export interface CreateStockMovementOperationRequest {
  reason: string
  movements: CreateStockMovementRequest[]
}

export interface StockMovementResponse {
  productId: string
  costPrice: number
  quantity: number
}

export interface StockMovementOperationResponse {
  movements: StockMovementResponse[]
}
