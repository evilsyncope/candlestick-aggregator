package com.baraka.json;

import com.baraka.domain.Candlestick;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class CandlesticksJson {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode serialize(List<Candlestick> candlesticks) {
        final var arrayNode = objectMapper.createArrayNode();
        candlesticks.forEach(candlestick -> arrayNode
            .add(objectMapper.createObjectNode()
                .put("startedAt", candlestick.startedAt.toString())
                .put("open", candlestick.open)
                .put("close", candlestick.close)
                .put("high", candlestick.high)
                .put("low", candlestick.low)
            ));
        return arrayNode;
    }
}
