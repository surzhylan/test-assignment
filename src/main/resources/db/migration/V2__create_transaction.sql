CREATE TABLE transactions (
    id bigserial primary key not null,
    account_from bigint,
    account_to bigint,
    currency varchar(255),
    amount decimal(19, 2),
    date_time timestamp,
    limit_exceeded boolean,
    monthly_limit_id bigint,
    foreign key (monthly_limit_id) references monthly_limits(id)
);