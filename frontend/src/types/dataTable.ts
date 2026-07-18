export type AppDataTableColumnType =
  | 'text'
  | 'number'
  | 'currency'
  | 'date'
  | 'boolean'

export interface AppDataTableColumn<T> {
  field: Extract<keyof T, string>
  header: string
  sortable?: boolean
  type?: AppDataTableColumnType
  currency?: string
  trueLabel?: string
  falseLabel?: string
  formatter?: (value: T[keyof T], row: T) => string
}
