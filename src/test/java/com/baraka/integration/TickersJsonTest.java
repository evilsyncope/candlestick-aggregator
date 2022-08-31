package com.baraka.integration;

import com.baraka.domain.Ticker;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.baraka.domain.Symbol.symbol;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TickersJsonTest {

    @Test
    void should_deserialize_tickers_json() {
        // given
        String json = "{\"data\":[{\"p\":80.06256685501131,\"s\":\"AAPL\",\"t\":1661907474294,\"v\":10}],\"type\":\"trade\"}";

        // when
        var tickers = TickersJson.deserialize(json);

        // then
        assertThat(tickers).isEqualTo(List.of(
            new Ticker(
                symbol("AAPL"),
                Instant.ofEpochMilli(1661907474294L),
                new BigDecimal("80.06256685501131"))));
    }
}