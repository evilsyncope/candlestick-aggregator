package com.baraka.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.baraka.domain.Symbol.symbol;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CandlestickTest {

    @Test
    void should_apply_ticker_price_as_new_close() {
        // given
        var startedAt = now();
        var open = new BigDecimal(5);
        var close = new BigDecimal(6);
        var high = new BigDecimal(7);
        var low = new BigDecimal(2);
        var candlestick = new Candlestick(symbol("AAPL"), startedAt, startedAt.plus(1, MINUTES), open, close, high, low);
        var tickerPrice = new BigDecimal(4);
        var ticker = new Ticker(symbol("AAPL"), now(), tickerPrice, TEN);
        var expected = new Candlestick(symbol("AAPL"), startedAt, startedAt.plus(1, MINUTES), open, tickerPrice, high, low);

        // when
        var aggregated = candlestick.aggregate(ticker);

        // then
        assertThat(aggregated).isEqualTo(expected);
    }

    @Test
    void should_apply_ticker_price_as_new_low() {
        // given
        var startedAt = now();
        var open = new BigDecimal(5);
        var close = new BigDecimal(6);
        var high = new BigDecimal(7);
        var low = new BigDecimal(2);
        var candlestick = new Candlestick(symbol("AAPL"), startedAt, startedAt.plus(1, MINUTES), open, close, high, low);
        var tickerPrice = new BigDecimal(1);
        var ticker = new Ticker(symbol("AAPL"), now(), tickerPrice, TEN);
        var expected = new Candlestick(symbol("AAPL"), startedAt, startedAt.plus(1, MINUTES), open, tickerPrice, high, tickerPrice);

        // when
        var aggregated = candlestick.aggregate(ticker);

        // then
        assertThat(aggregated).isEqualTo(expected);
    }

    @Test
    void should_apply_ticker_price_as_new_high() {
        // given
        var startedAt = now();
        var open = new BigDecimal(5);
        var close = new BigDecimal(6);
        var high = new BigDecimal(7);
        var low = new BigDecimal(2);
        var candlestick = new Candlestick(symbol("AAPL"), startedAt, startedAt.plus(1, MINUTES), open, close, high, low);
        var tickerPrice = new BigDecimal(8);
        var ticker = new Ticker(symbol("AAPL"), now(), tickerPrice, TEN);
        var expected = new Candlestick(symbol("AAPL"), startedAt, startedAt.plus(1, MINUTES), open, tickerPrice, tickerPrice, low);

        // when
        var aggregated = candlestick.aggregate(ticker);

        // then
        assertThat(aggregated).isEqualTo(expected);
    }

    @Test
    void should_fail_on_aggregating_non_matching_ticker() {
        // given
        var startedAt = now();
        var candlestick = new Candlestick(symbol("AAPL"), startedAt, startedAt.plus(1, MINUTES), ZERO, ZERO, ZERO, ZERO);
        var ticker = new Ticker(symbol("TSLA"), now(), ONE, TEN);

        // then
        assertThatThrownBy(() -> candlestick.aggregate(ticker))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Ticker of TSLA cannot be applied to AAPL");
    }
}