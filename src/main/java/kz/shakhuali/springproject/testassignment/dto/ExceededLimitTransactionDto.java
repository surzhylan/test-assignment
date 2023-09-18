package kz.shakhuali.springproject.testassignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class ExceededLimitTransactionDto{

    private Long id;
    private String currency;
    private BigDecimal amount;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    private String category;

    @JsonProperty("limit_sum")
    private BigDecimal limitSum;

    @JsonProperty("limit_date_time")
    private ZonedDateTime limitDatetime;

    @JsonProperty("limit_currency")
    private String limitCurrencyShortname;
}
