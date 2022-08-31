package com.baraka.domain;

import com.google.common.base.Preconditions;
import org.checkerframework.checker.units.qual.C;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Candlestick {

    public final Symbol symbol;
    public final Instant startedAt;
    public final Instant endsAt;
    public final BigDecimal open;
    public final BigDecimal close;
    public final BigDecimal high;
    public final BigDecimal low;

    public Candlestick(Symbol symbol,
                       Instant startedAt,
                       Instant endsAt,
                       BigDecimal open,
                       BigDecimal close,
                       BigDecimal high,
                       BigDecimal low) {
        this.symbol = symbol;
        this.startedAt = startedAt;
        this.endsAt = endsAt;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
    }

    public Candlestick aggregate(Ticker ticker) {
        Preconditions.checkArgument(ticker.symbol.equals(symbol), "Ticker of %s cannot be applied to %s", ticker.symbol, symbol);
        return new Candlestick(
            symbol,
            startedAt,
            endsAt,
            open,
            ticker.price,
            high.max(ticker.price),
            low.min(ticker.price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candlestick that = (Candlestick) o;
        return Objects.equals(symbol, that.symbol) && Objects.equals(startedAt, that.startedAt) && Objects.equals(endsAt, that.endsAt) && Objects.equals(open, that.open) && Objects.equals(close, that.close) && Objects.equals(high, that.high) && Objects.equals(low, that.low);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, startedAt, endsAt, open, close, high, low);
    }

    @Override
    public String toString() {
        return "Candlestick{" +
            "symbol=" + symbol +
            ", startedAt=" + startedAt +
            ", endsAt=" + endsAt +
            ", open=" + open +
            ", close=" + close +
            ", high=" + high +
            ", low=" + low +
            '}';
    }
}
