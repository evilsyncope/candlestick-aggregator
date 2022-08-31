package com.baraka.testfixtures;

import com.baraka.domain.Candlestick;

import java.math.BigDecimal;

import static com.baraka.domain.Symbol.symbol;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MINUTES;

public interface CandlestickTestData {

    default Candlestick aCandlestick() {
        return new Candlestick(
            symbol("AAPL"),
            now().truncatedTo(MINUTES),
            now().truncatedTo(MINUTES).plus(1, MINUTES),
            BigDecimal.valueOf(Math.random()),
            BigDecimal.valueOf(Math.random()),
            BigDecimal.valueOf(Math.random()),
            BigDecimal.valueOf(Math.random())
        );
    }
}
