#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"
ADMIN_USER="${ADMIN_USER:-admin}"
ADMIN_PASS="${ADMIN_PASS:-admin123}"
APP_USER="${APP_USER:-demo_user}"
APP_PASS="${APP_PASS:-user123}"

echo "[1/8] backend ping"
curl -fsS "$BASE_URL/api/auth/ping" | jq .

echo "[2/8] admin login"
ADMIN_TOKEN=$(curl -fsS -X POST "$BASE_URL/api/auth/login" \
  -H 'Content-Type: application/json' \
  -d "{\"username\":\"$ADMIN_USER\",\"password\":\"$ADMIN_PASS\"}" | jq -r '.data.token')

echo "[3/8] admin me"
curl -fsS "$BASE_URL/api/admin/me" -H "Authorization: Bearer $ADMIN_TOKEN" | jq .

echo "[4/8] admin products"
curl -fsS "$BASE_URL/api/admin/products" -H "Authorization: Bearer $ADMIN_TOKEN" | jq '.["data"] | length'

echo "[5/8] app login"
APP_TOKEN=$(curl -fsS -X POST "$BASE_URL/api/auth/login" \
  -H 'Content-Type: application/json' \
  -d "{\"username\":\"$APP_USER\",\"password\":\"$APP_PASS\"}" | jq -r '.data.token')

echo "[6/8] ordinary user must not access admin API"
HTTP_CODE=$(curl -sS -o /tmp/obai_forbidden.json -w '%{http_code}' "$BASE_URL/api/admin/users" -H "Authorization: Bearer $APP_TOKEN")
test "$HTTP_CODE" = "403"
cat /tmp/obai_forbidden.json | jq .

echo "[7/8] app home"
curl -fsS "$BASE_URL/api/app/home" | jq .

echo "[8/8] app points with token"
curl -fsS "$BASE_URL/api/app/points" -H "Authorization: Bearer $APP_TOKEN" | jq .

echo "smoke test passed"
