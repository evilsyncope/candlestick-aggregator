package com.baraka.aggregator;

import com.baraka.domain.Symbol;
import com.baraka.testfixtures.CandlestickTestData;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.baraka.domain.Symbol.symbol;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class CandlestickStorageTest implements CandlestickTestData {

    public static final Symbol AAPL = symbol("AAPL");

    @Test
    void should_return_empty_list_when_no_candlesticks_for_symbol() {
        // given
        var storage = new CandlestickStorage();

        // when
        var candlesticks = storage.candlesticksFor(AAPL);

        // then
        assertThat(candlesticks).isEmpty();
    }

    @Test
    void should_return_candlesticks_for_symbol() {
        // given
        var aaplCandlesticks = List.of(aCandlestick(), aCandlestick());
        var storage = new CandlestickStorage(Map.of(
            AAPL, new ArrayDeque<>(aaplCandlesticks),
            symbol("TSLA"), new ArrayDeque<>(List.of(aCandlestick())))
        );

        // when
        var candlesticks = storage.candlesticksFor(AAPL);

        // then
        assertThat(candlesticks).isEqualTo(aaplCandlesticks);
    }

    @Test
    void should_return_last_candlestick_for_symbol() {
        // given
        var lastCandlestick = aCandlestick();
        var aaplCandlesticks = List.of(aCandlestick(), lastCandlestick);
        var storage = new CandlestickStorage(Map.of(
            AAPL, new ArrayDeque<>(aaplCandlesticks)
        ));

        // when
        var candlestick = storage.lastCandlestickFor(AAPL);

        // then
        assertThat(candlestick).contains(lastCandlestick);
    }

    @Test
    void should_apply_candlestick_for_symbol() {
        // given
        var firstCandlestick = aCandlestick();
        var aaplCandlesticks = List.of(aCandlestick(), aCandlestick());
        var newCandlestick = aCandlestick();
        var storage = new CandlestickStorage(new HashMap<>(Map.of(
            AAPL, new ArrayDeque<>(aaplCandlesticks)
        )));

        // when
        storage.applyCandlestickFor(newCandlestick);

        // then
        assertThat(storage.candlesticksFor(AAPL)).isEqualTo(Stream.concat(aaplCandlesticks.stream(), Stream.of(newCandlestick)).collect(toList()));
    }

    @Test
    void should_replace_last_candlestick_for_symbol() {
        // given
        var firstCandlestick = aCandlestick();
        var aaplCandlesticks = List.of(firstCandlestick, aCandlestick());
        var replacement = aCandlestick();
        var storage = new CandlestickStorage(new HashMap<>(Map.of(
            AAPL, new ArrayDeque<>(aaplCandlesticks)
        )));

        // when
        storage.replaceLastCandlestick(replacement);

        // then
        assertThat(storage.candlesticksFor(AAPL)).isEqualTo(List.of(firstCandlestick, replacement));
    }
}