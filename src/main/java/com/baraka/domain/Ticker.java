package com.baraka.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Ticker {

    public final Symbol symbol;
    public final Instant time;
    public final BigDecimal price;

    public Ticker(Symbol symbol, Instant time, BigDecimal price) {
        this.symbol = symbol;
        this.time = time;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticker ticker = (Ticker) o;
        return Objects.equals(symbol, ticker.symbol) && Objects.equals(time, ticker.time) && Objects.equals(price, ticker.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, time, price);
    }

    @Override
    public String toString() {
        return "Ticker{" +
            "symbol=" + symbol +
            ", time=" + time +
            ", price=" + price +
            '}';
    }
}
