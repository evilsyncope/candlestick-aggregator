package com.baraka.aggregator;

import com.baraka.domain.Candlestick;
import com.baraka.domain.Symbol;
import com.baraka.domain.Ticker;

import java.time.Duration;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.stream.Collectors.toUnmodifiableList;

public class CandlestickAggregator {

    private static final Duration CANDLESTICK_DURATION = Duration.ofMinutes(1);

    // todo empty candlesticks
    private final Map<Symbol, Deque<Candlestick>> candlestickStorage;

    public CandlestickAggregator() {
        this.candlestickStorage = new ConcurrentHashMap<>();
    }

    public CandlestickAggregator(Map<Symbol, Deque<Candlestick>> initialCandlesticks) {
        this.candlestickStorage = initialCandlesticks;
    }

    public List<Candlestick> candlesticksFor(Symbol symbol) {
        // todo
        return candlestickStorage.get(symbol).stream().collect(toUnmodifiableList());
    }

    public void apply(Ticker ticker) {
        final var candlesticks = candlestickStorage.computeIfAbsent(ticker.symbol, symbol -> new LinkedBlockingDeque<>());
        final var lastCandlestick = Optional.ofNullable(candlesticks.pollLast());
        final var startedAt = ticker.time.truncatedTo(MINUTES);
        final var endsAt = startedAt.plus(CANDLESTICK_DURATION);
        final var aggregated = lastCandlestick.map(last -> last.endsAt.isBefore(ticker.time)
                                                           ? new Candlestick(ticker.symbol, startedAt, endsAt, ticker.price, ticker.price, ticker.price, ticker.price)
                                                           : last.aggregate(ticker))
            // todo to candlestick
            .orElseGet(() -> new Candlestick(ticker.symbol, startedAt, endsAt, ticker.price, ticker.price, ticker.price, ticker.price));

        // todo fix removed last
        candlesticks.offer(aggregated);
    }
}
