package kz.shakhuali.springproject.testassignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TransactionDto {

    private Long id;
    private String currency;
    private BigDecimal amount;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;
}
