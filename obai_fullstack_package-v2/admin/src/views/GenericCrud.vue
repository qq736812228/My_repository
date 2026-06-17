<template>
  <div class="page-card">
    <div class="toolbar">
      <div>
        <div class="page-title">{{ title }}</div>
        <div class="hint">接口：/api/admin/{{ endpoint }}</div>
      </div>
      <div>
        <el-button @click="load">刷新</el-button>
        <el-button type="success" @click="openCreate">新增</el-button>
      </div>
    </div>

    <el-table :data="rows" stripe border height="620">
      <el-table-column prop="id" label="ID" width="80" fixed />
      <el-table-column
        v-for="field in displayFields"
        :key="field.key"
        :prop="field.key"
        :label="field.label"
        min-width="150"
        show-overflow-tooltip
      >
        <template #default="scope">{{ formatCell(scope.row[field.key]) }}</template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="180">
        <template #default="scope">
          <el-button link type="primary" @click="openEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除？" @confirm="remove(scope.row.id)">
            <template #reference><el-button link type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="editing?.id ? '编辑' : '新增'" width="720px">
      <el-form label-width="140px">
        <el-form-item v-for="field in editableFields" :key="field.key" :label="field.label">
          <el-switch v-if="field.type === 'switch'" v-model="form[field.key]" />
          <el-input-number v-else-if="field.type === 'number'" v-model="form[field.key]" />
          <el-date-picker
            v-else-if="field.type === 'date'"
            v-model="form[field.key]"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="选择日期"
          />
          <el-date-picker
            v-else-if="field.type === 'datetime'"
            v-model="form[field.key]"
            type="datetime"
            value-format="YYYY-MM-DDTHH:mm:ssZ"
            placeholder="选择时间"
          />
          <el-input
            v-else
            v-model="form[field.key]"
            :type="field.type === 'textarea' ? 'textarea' : 'text'"
            :autosize="{ minRows: 1, maxRows: 5 }"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="success" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { crud } from '@/api/modules'
import { crudSchemas, type CrudField } from '@/config/crudSchemas'

const route = useRoute()
const rows = ref<Record<string, any>[]>([])
const dialogVisible = ref(false)
const editing = ref<Record<string, any> | null>(null)
const form = reactive<Record<string, any>>({})
const endpoint = computed(() => String(route.meta.endpoint || 'users'))
const title = computed(() => String(route.meta.title || endpoint.value))
const schema = computed(() => crudSchemas[endpoint.value])
const fallbackFields = computed<CrudField[]>(() => {
  const first = rows.value[0] || {}
  return Object.keys(first)
    .filter((key) => !['id', 'deleted'].includes(key))
    .slice(0, 12)
    .map((key) => ({ key, label: key }))
})
const displayFields = computed(() => schema.value?.fields || fallbackFields.value)
const editableFields = computed(() => displayFields.value.filter((field) => !field.readonly))

async function load() {
  rows.value = await crud.list(endpoint.value)
}

function defaultValue(field: CrudField) {
  if (field.type === 'number') return 0
  if (field.type === 'switch') return true
  return ''
}

function openCreate() {
  editing.value = null
  Object.keys(form).forEach((key) => delete form[key])
  editableFields.value.forEach((field) => (form[field.key] = defaultValue(field)))
  dialogVisible.value = true
}

function openEdit(row: Record<string, any>) {
  editing.value = row
  Object.keys(form).forEach((key) => delete form[key])
  editableFields.value.forEach((field) => (form[field.key] = row[field.key] ?? defaultValue(field)))
  dialogVisible.value = true
}

function payload() {
  return editableFields.value.reduce<Record<string, unknown>>((acc, field) => {
    acc[field.key] = form[field.key]
    return acc
  }, {})
}

async function save() {
  if (editing.value?.id) {
    await crud.update(endpoint.value, editing.value.id, payload())
  } else {
    await crud.create(endpoint.value, payload())
  }
  ElMessage.success('已保存')
  dialogVisible.value = false
  await load()
}

async function remove(id: number) {
  await crud.remove(endpoint.value, id)
  ElMessage.success('已删除')
  await load()
}

function formatCell(value: unknown) {
  if (Array.isArray(value)) return value.length ? `共 ${value.length} 项` : '-'
  if (value && typeof value === 'object') return JSON.stringify(value)
  if (value === null || typeof value === 'undefined' || value === '') return '-'
  return String(value)
}

watch(() => route.fullPath, load)
onMounted(load)
</script>
<style scoped>
.toolbar { display:flex; align-items:center; justify-content:space-between; margin-bottom:12px; }
.hint { color:#7b8c80; font-size:12px; margin-top:4px; }
</style>
