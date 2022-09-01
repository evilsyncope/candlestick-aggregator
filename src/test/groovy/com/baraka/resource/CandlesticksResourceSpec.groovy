import com.baraka.CandlesticksApp
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class CandlesticksResourceSpec extends Specification {

    static def app = new CandlesticksApp()

    def client = HttpClient.newHttpClient()
    def objectMapper = new ObjectMapper()

    static {
        app.start()
    }

    def 'should return candlestick list'() {
        given:
        def symbol = 'AAPL'
        def request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8080/candlesticks?symbol=${symbol}"))
            .GET()
            .build()

        when:
        Thread.sleep(1000)
        def response = client.send(request, HttpResponse.BodyHandlers.ofString()).body()

        then:
        def result = objectMapper.readValue(response, Map.class)
        def candlesticks = result.get('values') as List
        assertThat(result.get('symbol')).isEqualTo('AAPL')
        assertThat(candlesticks.size()).isEqualTo(1)
    }
}