CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

GRANT ALL PRIVILEGES ON DATABASE listing TO listing;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO listing;

CREATE TABLE listing(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255),
    description TEXT,
    owner_id VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    type VARCHAR(255) NOT NULL,
    featured BOOLEAN NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_listing_owner_id ON listing (owner_id);

CREATE TABLE listing_account_balance(
    id SERIAL PRIMARY KEY,
    accountId UUID NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_listing_account_balance_accountId ON listing_account_balance (accountId);

CREATE TABLE listing_credit(
    id SERIAL PRIMARY KEY,
    available boolean NOT NULL DEFAULT true,
    transaction_id UUID NOT NULL,
    listing_id UUID NOT NULL,
    balance_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reserved_at TIMESTAMP,
    expires_at TIMESTAMP,

    FOREIGN KEY (listing_id) REFERENCES listing(id),
    FOREIGN KEY (balance_id) REFERENCES listing_account_balance(id)
);
CREATE INDEX idx_balance_id_listing_credit ON listing_credit (balance_id);
