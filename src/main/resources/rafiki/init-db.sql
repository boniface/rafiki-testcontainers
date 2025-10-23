-- Initialize databases for Rafiki
-- Create databases if they don't exist
SELECT 'CREATE DATABASE authdb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'authdb')\gexec

SELECT 'CREATE DATABASE backenddb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'backenddb')\gexec
