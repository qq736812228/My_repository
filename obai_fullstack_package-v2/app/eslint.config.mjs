import js from '@eslint/js'
import vue from 'eslint-plugin-vue'
import prettier from 'eslint-config-prettier'

// ESLint v9 flat config for the uni-app mini-program project.
export default [
  { ignores: ['dist/**', 'node_modules/**', 'unpackage/**'] },
  js.configs.recommended,
  ...vue.configs['flat/recommended'],
  {
    files: ['**/*.{js,vue}'],
    languageOptions: {
      ecmaVersion: 'latest',
      sourceType: 'module',
      globals: {
        // uni-app / WeChat mini-program runtime globals
        uni: 'readonly',
        wx: 'readonly',
        getApp: 'readonly',
        getCurrentPages: 'readonly',
        plus: 'readonly',
        UniApp: 'readonly',
        console: 'readonly'
      }
    },
    rules: {
      'vue/multi-word-component-names': 'off'
    }
  },
  prettier
]
