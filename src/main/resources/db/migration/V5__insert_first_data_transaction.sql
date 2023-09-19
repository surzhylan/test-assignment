INSERT INTO transactions (account_from, account_to, currency, amount, date_time, limit_exceeded, monthly_limit_id)
VALUES
    (12345, 54321, 'USD', 500.50, '2023-09-19 10:30:00', false, 1),
    (67890, 98765, 'USD', 800.75, '2023-09-20 14:45:00', true, 1);