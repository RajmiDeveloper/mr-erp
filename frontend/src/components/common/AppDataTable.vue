<script setup lang="ts" generic="T extends object">
import Column from 'primevue/column'
import DataTable, {
  type DataTablePageEvent,
  type DataTableSortEvent,
} from 'primevue/datatable'
import type { AppDataTableColumn } from '@/types/dataTable'

const props = withDefaults(
  defineProps<{
    items: T[]
    columns: AppDataTableColumn<T>[]
    dataKey?: string
    loading?: boolean
    paginator?: boolean
    rows?: number
    rowsPerPageOptions?: number[]
    emptyMessage?: string
    sortMode?: 'single' | 'multiple'
    removableSort?: boolean
    lazy?: boolean
    totalRecords?: number
  }>(),
  {
    dataKey: 'id',
    loading: false,
    paginator: true,
    rows: 10,
    rowsPerPageOptions: () => [5, 10, 20, 50],
    emptyMessage: 'No hay registros para mostrar.',
    sortMode: 'single',
    removableSort: true,
    lazy: false,
    totalRecords: undefined,
  },
)

const emit = defineEmits<{
  page: [event: DataTablePageEvent]
  sort: [event: DataTableSortEvent]
}>()

function getValue(row: T, field: Extract<keyof T, string>): T[keyof T] {
  return row[field]
}

function formatValue(row: T, column: AppDataTableColumn<T>): string {
  const value = getValue(row, column.field)

  if (column.formatter) {
    return column.formatter(value, row)
  }

  if (value === null || value === undefined) {
    return '—'
  }

  switch (column.type) {
    case 'currency':
      return new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: column.currency ?? 'ARS',
      }).format(Number(value))
    case 'number':
      return new Intl.NumberFormat('es-AR').format(Number(value))
    case 'date':
      return new Intl.DateTimeFormat('es-AR').format(new Date(String(value)))
    case 'boolean':
      return Boolean(value)
        ? (column.trueLabel ?? 'Sí')
        : (column.falseLabel ?? 'No')
    default:
      return String(value)
  }
}
</script>

<template>
  <DataTable
    :value="items"
    :data-key="dataKey"
    :loading="loading"
    :paginator="paginator"
    :rows="rows"
    :rows-per-page-options="rowsPerPageOptions"
    :empty-message="emptyMessage"
    :sort-mode="sortMode"
    :removable-sort="removableSort"
    :lazy="lazy"
    :total-records="totalRecords ?? items.length"
    paginator-template="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown CurrentPageReport"
    current-page-report-template="{first} a {last} de {totalRecords}"
    striped-rows
    @page="emit('page', $event)"
    @sort="emit('sort', $event)"
  >
    <Column
      v-for="column in columns"
      :key="column.field"
      :field="column.field"
      :header="column.header"
      :sortable="column.sortable ?? true"
    >
      <template #body="{ data }">
        <slot
          :name="`cell-${column.field}`"
          :data="data as T"
          :value="getValue(data as T, column.field)"
          :column="column"
        >
          {{ formatValue(data as T, column) }}
        </slot>
      </template>
    </Column>

    <template #empty>
      <slot name="empty">
        {{ emptyMessage }}
      </slot>
    </template>
  </DataTable>
</template>

<style scoped>
:deep(.p-datatable) {
  background: #374151;
}

:deep(.p-datatable-thead > tr > th) {
  color: #f9fafb;
  background: #374151;
  border-color: #4b5563;
}

:deep(.p-datatable-tbody > tr) {
  color: #f9fafb;
  background: #374151;
}

:deep(.p-datatable-tbody > tr.p-row-odd) {
  color: #f9fafb;
  background: #303a48 !important;
}

:deep(.p-datatable-tbody > tr > td) {
  border-color: #4b5563;
}

:deep(.p-datatable-column-sorted) {
  color: #f9fafb;
  background: #374151;
}

:deep(.p-datatable-column-sorted .p-datatable-sort-icon) {
  color: #cbd5e1;
}

:deep(.p-datatable-sort-icon) {
  color: #cbd5e1;
}

:deep(.p-datatable-paginator-bottom[data-p-bottom='true'] > .p-paginator) {
  border-radius: 0 0 var(--p-paginator-border-radius, 6px)
    var(--p-paginator-border-radius, 6px) !important;
  color: #cbd5e1;
  background: #374151;
}

:deep(.p-datatable-paginator-bottom[data-p-bottom='true']) {
  border-bottom: 0;
}

:deep(.p-paginator-page),
:deep(.p-paginator-first),
:deep(.p-paginator-prev),
:deep(.p-paginator-next),
:deep(.p-paginator-last) {
  color: #cbd5e1;
}
</style>
