package com.baraka.aggregator;

import com.baraka.domain.Candlestick;
import com.baraka.domain.Ticker;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.MINUTES;

public class CandlestickAggregator {

    private static final Duration CANDLESTICK_DURATION = Duration.ofMinutes(1);

    private final CandlestickStorage candlestickStorage;

    public CandlestickAggregator(CandlestickStorage candlestickStorage) {
        this.candlestickStorage = candlestickStorage;
    }

    public void apply(Ticker ticker) {
        final var lastCandlestick = candlestickStorage.lastCandlestickFor(ticker.symbol);
        final var startedAt = ticker.time.truncatedTo(MINUTES);
        final var endsAt = startedAt.plus(CANDLESTICK_DURATION);
        final var aggregated = lastCandlestick
            .filter(last -> !last.endsAt.isBefore(ticker.time))
            .map(last -> last.aggregate(ticker))
            .orElseGet(() -> new Candlestick(ticker, startedAt, endsAt));

        if (lastCandlestick.map(c -> c.startedAt.equals(aggregated.startedAt)).orElse(false)) {
            candlestickStorage.replaceLastCandlestick(aggregated);
        } else {
            candlestickStorage.applyCandlestickFor(aggregated);
        }
    }
}
