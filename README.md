# candlestick-aggregator

This app uses Spark Java to provide REST interface and to collect stock prices via websocket connection. All candlesticks are stored in memory. `CandlestickStorage` also can utilise some external key-value storage like `Redis`.


## How to use it:

The request format is
<code>GET http://localhost:8080/candlesticks?symbol=TSLA.</code>

Gradle build:
<code>./gradlew build</code>

Build docker:
<code>docker build -t candlestick-aggregator .</code>

Run this container:
<code>docker run -it --rm -p 8080:8080 candlestick-aggregator</code>
