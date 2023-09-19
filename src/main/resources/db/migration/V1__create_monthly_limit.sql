CREATE TABLE monthly_limits (
    id bigserial primary key not null,
    category varchar(255),
    amount_limit decimal(19, 2),
    timestamp timestamp
);