package com.baraka.json;

import com.baraka.domain.Candlestick;
import com.baraka.domain.Symbol;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class CandlesticksJson {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode serialize(Symbol symbol, List<Candlestick> candlesticks) {
        final var result = objectMapper.createObjectNode();
        final var arrayNode = objectMapper.createArrayNode();
        result.put("symbol", symbol.toString());
        result.set("values", arrayNode);
        candlesticks.forEach(candlestick -> arrayNode
            .add(objectMapper.createObjectNode()
                .put("startedAt", candlestick.startedAt.toString())
                .put("open", candlestick.open)
                .put("close", candlestick.close)
                .put("high", candlestick.high)
                .put("low", candlestick.low)
            ));
        return result;
    }
}
