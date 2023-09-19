package kz.shakhuali.springproject.testassignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class ExceededLimitTransactionDto{

    @Schema(description = "Уникальный идентификатор транзакции")
    private Long id;

    @Schema(description = "Валюта транзакции")
    private String currency;

    @Schema(description = "Сумма транзакции")
    private BigDecimal amount;

    @JsonProperty("date_time")
    @Schema(description = "Дата и время транзакции")
    private LocalDateTime dateTime;

    @Schema(description = "Категория лимита")
    private String category;

    @JsonProperty("limit_sum")
    @Schema(description = "Сумма лимита")
    private BigDecimal limitSum;

    @JsonProperty("limit_date_time")
    @Schema(description = "Дата и время установки лимита")
    private ZonedDateTime limitDatetime;

    @JsonProperty("limit_currency")
    @Schema(description = "Валюта лимита")
    private String limitCurrencyShortname;
}
