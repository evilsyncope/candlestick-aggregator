package com.baraka.aggregator;

import com.baraka.domain.Candlestick;
import com.baraka.domain.Symbol;

import java.util.List;

public interface CandlestickProvider {

    List<Candlestick> candlesticksFor(Symbol symbol);
}
