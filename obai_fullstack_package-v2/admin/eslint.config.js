import vue from 'eslint-plugin-vue'
import tsParser from '@typescript-eslint/parser'
import tsPlugin from '@typescript-eslint/eslint-plugin'
import prettier from 'eslint-config-prettier'

// ESLint v9 flat config (migrated from the legacy .eslintrc.cjs).
export default [
  { ignores: ['dist/**', 'node_modules/**'] },
  ...vue.configs['flat/recommended'],
  {
    files: ['**/*.ts'],
    languageOptions: {
      parser: tsParser,
      ecmaVersion: 'latest',
      sourceType: 'module'
    },
    plugins: { '@typescript-eslint': tsPlugin },
    rules: {
      ...tsPlugin.configs.recommended.rules,
      // This admin is a generic schema-driven CRUD scaffold that is intentionally
      // dynamic (Record<string, any>), so explicit `any` is allowed by design.
      '@typescript-eslint/no-explicit-any': 'off'
    }
  },
  {
    // .vue files are parsed by vue-eslint-parser (from the vue flat config);
    // delegate the <script lang="ts"> block to the TypeScript parser.
    files: ['**/*.vue'],
    languageOptions: {
      parserOptions: {
        parser: tsParser,
        ecmaVersion: 'latest',
        sourceType: 'module'
      }
    },
    plugins: { '@typescript-eslint': tsPlugin },
    rules: {
      ...tsPlugin.configs.recommended.rules,
      '@typescript-eslint/no-explicit-any': 'off',
      'vue/multi-word-component-names': 'off'
    }
  },
  prettier
]
