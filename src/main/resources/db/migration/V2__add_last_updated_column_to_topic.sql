ALTER TABLE Topic ADD COLUMN last_updated TIMESTAMP;

UPDATE Topic SET last_updated = creation_date;

ALTER TABLE Topic MODIFY last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP;