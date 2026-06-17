-- OBAI PostgreSQL database bootstrap
-- 手工部署时先执行：psql -U postgres -f sql/00_create_database.sql
SELECT 'CREATE DATABASE obai'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'obai')\gexec
