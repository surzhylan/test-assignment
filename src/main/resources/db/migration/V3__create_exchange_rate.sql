CREATE TABLE exchange_rates (
    id bigserial primary key not null,
    base_currency varchar(255),
    target_currency varchar(255),
    rate double precision,
    date date
);