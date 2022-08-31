package com.baraka.aggregator;

import com.baraka.domain.Candlestick;
import com.baraka.domain.Symbol;

import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.stream.Collectors.toUnmodifiableList;

public class CandlestickStorage implements CandlestickProvider {

    // todo empty candlesticks
    private final Map<Symbol, Deque<Candlestick>> candlestickStorage;

    public CandlestickStorage() {
        this.candlestickStorage = new ConcurrentHashMap<>();
    }

    public CandlestickStorage(Map<Symbol, Deque<Candlestick>> initialCandlesticks) {
        this.candlestickStorage = initialCandlesticks;
    }

    public List<Candlestick> candlesticksFor(Symbol symbol) {
        // todo
        return Optional.ofNullable(candlestickStorage.get(symbol))
            .map(candlesticks -> candlesticks.stream().collect(toUnmodifiableList()))
            .orElseGet(Collections::emptyList);
    }

    public Optional<Candlestick> lastCandlestickFor(Symbol symbol) {
        return Optional.ofNullable(candlestickStorage.get(symbol)).map(Deque::getLast);
    }

    public void applyCandlestickFor(Candlestick candlestick) {
        final var candlesticks = candlestickStorage.computeIfAbsent(candlestick.symbol, s -> new LinkedBlockingDeque<>());
        candlesticks.offer(candlestick);
    }

    public void replaceLastCandlestick(Candlestick replacement) {
        final var candlesticks = candlestickStorage.computeIfAbsent(replacement.symbol, s -> new LinkedBlockingDeque<>());
        candlesticks.pollLast();
        candlesticks.offer(replacement);
    }
}
