package kz.shakhuali.springproject.testassignment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор транзакции")
    private Long id;

    @Column(name = "account_from")
    @Schema(description = "Номер счета отправителя")
    private Long accountFrom;

    @Column(name = "account_to")
    @Schema(description = "Номер счета получателя")
    private Long accountTo;

    @Schema(description = "Валюта транзакции")
    private String currency;

    @Schema(description = "Сумма транзакции")
    private BigDecimal amount;

    @Schema(description = "Дата и время транзакции")
    private LocalDateTime dateTime;

    @Column(name = "limit_exceeded")
    @Schema(description = "Флаг превышения лимита")
    private boolean limitExceeded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_limit_id")
    @Schema(description = "Идентификатор месячного лимита")
    private MonthlyLimit monthlyLimit;
}
