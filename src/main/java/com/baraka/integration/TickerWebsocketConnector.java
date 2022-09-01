package com.baraka.integration;

import com.baraka.aggregator.CandlestickAggregator;
import com.baraka.aggregator.CandlestickStorage;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebSocket
public class TickerWebsocketConnector {

    private static final Logger LOG = LoggerFactory.getLogger(TickerWebsocketConnector.class);
    private final CandlestickAggregator candlestickAggregator;

    public TickerWebsocketConnector(CandlestickStorage candlestickStorage) {
        candlestickAggregator = new CandlestickAggregator(candlestickStorage);
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        final var tickers = TickersJson.deserialize(message);
        tickers.forEach(candlestickAggregator::apply);
    }
}


