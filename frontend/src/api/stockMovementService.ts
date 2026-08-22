import { http } from './http'
import type {
  CreateStockMovementOperationRequest,
  StockMovementOperationResponse,
} from '@/types/stockMovement'

export function createStockMovementOperation(
  operation: CreateStockMovementOperationRequest,
): Promise<StockMovementOperationResponse> {
  return http<StockMovementOperationResponse>('/api/stockMovement', {
    method: 'POST',
    body: JSON.stringify(operation),
  })
}
