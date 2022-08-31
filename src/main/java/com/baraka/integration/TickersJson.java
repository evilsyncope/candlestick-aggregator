package com.baraka.integration;

import com.baraka.domain.Ticker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

import static com.baraka.domain.Symbol.symbol;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class TickersJson {

    private static ObjectMapper mapper = new ObjectMapper();

    public static Collection<Ticker> deserialize(String json) {
        final var jsonNode = parse(json);
        return stream(jsonNode.get("data").spliterator(), false)
            .map(ticker -> new Ticker(
                symbol(ticker.get("s").asText()),
                Instant.ofEpochMilli(ticker.get("t").longValue()),
                ticker.get("p").decimalValue()))
            .collect(toList());
    }

    private static JsonNode parse(String json) {
        try {
            return mapper.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
