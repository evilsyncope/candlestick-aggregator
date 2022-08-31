package com.baraka.integration;

import com.baraka.aggregator.CandlestickAggregator;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebSocket
public class TickerWebsocketConnector {

    private static final Logger LOG = LoggerFactory.getLogger(TickerWebsocketConnector.class);
    private final CandlestickAggregator candlestickAggregator;

    public TickerWebsocketConnector() {
        candlestickAggregator = new CandlestickAggregator();
    }

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        LOG.info("Connected!");
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        LOG.info("Disconnected!");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        final var tickers = TickersJson.deserialize(message);
        tickers.forEach(candlestickAggregator::apply);
    }
}


