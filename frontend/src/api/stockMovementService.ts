import { http } from './http'
import type {
  CreateStockMovementRequest,
  StockMovementResponse,
} from '@/types/stockMovement'

export function createStockMovement(
  movement: CreateStockMovementRequest,
): Promise<StockMovementResponse> {
  return http<StockMovementResponse>('/api/stockMovement', {
    method: 'POST',
    body: JSON.stringify(movement),
  })
}
