package com.baraka;

import com.baraka.integration.TickerWebsocketConnector;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import spark.Spark;

import java.io.IOException;
import java.net.URI;

public class CandlesticksApp {

    public static final URI TICKERS_WEBSOCKET_URL = URI.create("ws://b-mocks.dev.app.getbaraka.com:9989");
    private final WebSocketClient webSocketClient;

    public CandlesticksApp() {
        this.webSocketClient = new WebSocketClient();
    }

    public void connect() throws Exception {
        // todo upgrade request?
        webSocketClient.start();
        webSocketClient.connect(new TickerWebsocketConnector(), TICKERS_WEBSOCKET_URL, new ClientUpgradeRequest());
    }

    public static void main(String[] args) throws Exception {
        Spark.init();
        new CandlesticksApp().connect();
        Spark.awaitInitialization();
    }
}
