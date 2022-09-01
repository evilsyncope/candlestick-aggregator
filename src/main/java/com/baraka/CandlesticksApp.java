package com.baraka;

import com.baraka.aggregator.CandlestickProvider;
import com.baraka.aggregator.CandlestickStorage;
import com.baraka.integration.TickerWebsocketConnector;
import com.baraka.resource.CandlesticksResource;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import spark.Service;
import spark.Spark;

import java.io.IOException;
import java.net.URI;

import static spark.Service.ignite;

public class CandlesticksApp {

    private static final URI TICKERS_WEBSOCKET_URL = URI.create("ws://b-mocks.dev.app.getbaraka.com:9989");
    private static final int PORT = 8080;

    private final WebSocketClient webSocketClient;
    private final CandlestickStorage candlestickStorage = new CandlestickStorage();

    public CandlesticksApp() {
        this.webSocketClient = new WebSocketClient();
    }

    public void start() throws Exception {
        final var service = ignite().port(PORT);
        new CandlesticksResource(service, candlestickStorage);

        Spark.init();
        Spark.awaitInitialization();
        connect();
    }

    public static void main(String[] args) throws Exception {
        new CandlesticksApp().start();
    }

    private void connect() throws Exception {
        webSocketClient.start();
        webSocketClient.connect(new TickerWebsocketConnector(candlestickStorage), TICKERS_WEBSOCKET_URL, new ClientUpgradeRequest());
    }
}
