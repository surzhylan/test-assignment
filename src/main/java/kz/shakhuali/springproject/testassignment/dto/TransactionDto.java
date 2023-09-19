package kz.shakhuali.springproject.testassignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    @Schema(description = "Уникальный идентификатор транзакции")
    private Long id;

    @JsonProperty("account_from")
    @Schema(description = "Номер счета отправителя")
    private Long accountFrom;

    @JsonProperty("account_to")
    @Schema(description = "Номер счета получателя")
    private Long accountTo;

    @Schema(description = "Валюта транзакции")
    private String currency;

    @Schema(description = "Сумма транзакции")
    private BigDecimal amount;

    @JsonProperty("date_time")
    @Schema(description = "Дата и время транзакции")
    private LocalDateTime dateTime;

    @Schema(description = "Идентификатор месячного лимита")
    private Long monthlyLimit;
}
