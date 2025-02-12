DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'techdecode') THEN
        CREATE DATABASE techdecode;
    END IF;
END
$$;
