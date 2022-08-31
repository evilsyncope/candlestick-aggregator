package com.baraka.aggregator;

import com.baraka.domain.Candlestick;
import com.baraka.domain.Symbol;
import com.baraka.domain.Ticker;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import static com.baraka.domain.Symbol.symbol;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CandlestickAggregatorTest {

    private static final Symbol AAPL = symbol("AAPL");

    private final CandlestickAggregator aggregator = new CandlestickAggregator();

    @Test
    void should_apply_first_candlestick_for_symbol() {
        // given
        var tickerPrice = new BigDecimal(4);
        var ticker = new Ticker(AAPL, now(), tickerPrice);
        var startedAt = now().truncatedTo(MINUTES);
        var expected = new Candlestick(AAPL, startedAt, startedAt.plus(1, MINUTES), tickerPrice, tickerPrice, tickerPrice, tickerPrice);

        // when
        aggregator.apply(ticker);

        // then
        assertThat(aggregator.candlesticksFor(AAPL)).isEqualTo(List.of(expected));
    }

    @Test
    void should_update_last_candlestick_for_symbol() {
        // given
        var tickerPrice = new BigDecimal(4);
        var ticker = new Ticker(AAPL, now(), tickerPrice);
        var startedAt = now().truncatedTo(MINUTES);
        var open = new BigDecimal(5);
        var close = new BigDecimal(6);
        var high = new BigDecimal(7);
        var low = new BigDecimal(2);
        var initialCandlestick1 = new Candlestick(symbol("AAPL"), startedAt.minus(1, MINUTES), startedAt, ONE, ONE, ONE, ONE);
        var initialCandlestick2 = new Candlestick(symbol("AAPL"), startedAt, startedAt.plus(1, MINUTES), open, close, high, low);
        var expected = new Candlestick(AAPL, startedAt, startedAt.plus(1, MINUTES), open, tickerPrice, high, low);
        var initialCandlesticks = List.of(initialCandlestick1, initialCandlestick2);
        var initialState = new HashMap<Symbol, Deque<Candlestick>>(Map.of(AAPL, new LinkedBlockingDeque<>(initialCandlesticks)));
        var aggregatorWithState = new CandlestickAggregator(initialState);

        // when
        aggregatorWithState.apply(ticker);

        // then
        assertThat(aggregatorWithState.candlesticksFor(AAPL)).isEqualTo(List.of(initialCandlestick1, expected));
    }
}