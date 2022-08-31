package com.baraka.resource;

import com.baraka.aggregator.CandlestickStorage;
import com.baraka.domain.Symbol;
import com.baraka.json.CandlesticksJson;
import com.fasterxml.jackson.databind.JsonNode;
import spark.Request;
import spark.Response;
import spark.Service;

import java.util.Locale;

import static com.baraka.domain.Symbol.symbol;

public class CandlesticksResource  {

    private final CandlestickStorage candlestickStorage;

    public CandlesticksResource(Service service, CandlestickStorage candlestickStorage) {
        this.candlestickStorage = candlestickStorage;
        service.get("/candlesticks", this::candlesticks);
    }

    private JsonNode candlesticks(Request rq, Response rs) {
        final var symbol = symbol(rq.queryParams("symbol").toUpperCase());
        return CandlesticksJson.serialize(candlestickStorage.candlesticksFor(symbol));
    }
}
