package com.baraka.integration

import com.baraka.domain.Ticker
import spock.lang.Specification

import java.time.Instant

import static com.baraka.domain.Symbol.symbol
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class TickersJsonTest extends Specification {

    def 'should deserialize tickers json'() {
        given:
        def json = """
        {
            "data": [
                {
                    "p": 80.06256685501131,
                    "s": "AAPL",
                    "t": 1661907474294,
                    "v": 10
                }
            ],
            "type":"trade"
        }
        """

        when:
        def tickers = TickersJson.deserialize(json);

        then:
        assertThat(tickers).isEqualTo(List.of(
            new Ticker(
                symbol("AAPL"),
                Instant.ofEpochMilli(1661907474294L),
                new BigDecimal("80.06256685501131"))));
    }
}
