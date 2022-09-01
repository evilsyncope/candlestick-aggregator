package com.baraka.json

import com.baraka.domain.Symbol
import com.baraka.testfixtures.CandlestickTestData
import spock.lang.Specification

import static com.baraka.domain.Symbol.symbol
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals

class CandlesticksJsonTest extends Specification implements CandlestickTestData {

    private static final Symbol AAPL = symbol('AAPL')

    def 'should serialize candlesticks'() {
        given:
        def candlestick = aCandlestick()

        when:
        def json = CandlesticksJson.serialize(AAPL, [candlestick])

        then:
        assertJsonEquals(json, """
            {
                "symbol": "AAPL",
                "values": [
                    {
                        "startedAt": "${candlestick.startedAt}",
                        "open": ${candlestick.open},
                        "close": ${candlestick.close},
                        "high": ${candlestick.high},
                        "low": ${candlestick.low}
                    }
                ]
            }
        """ as String)
    }

    def 'should serialize empty candlesticks list'() {
        given:
        def candlesticks = []

        when:
        def json = CandlesticksJson.serialize(AAPL, candlesticks)

        then:
        assertJsonEquals(json, """
            {
                "symbol": "AAPL",
                "values": []
            }
        """ as String)
    }
}
