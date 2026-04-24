package guru.springframework.spring7resttemplate.client;

import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.BeerDTOPageImpl;
import guru.springframework.spring7resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(BeerClientImpl.class)
class BeerClientMockTest {

    static final String URL = "http://localhost:8080";

    @Autowired
    BeerClient beerClient;

    @Autowired
    MockRestServiceServer server;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testListBeers() {
        String payload = objectMapper.writeValueAsString(getPage());
        server.expect(method(HttpMethod.GET))
                .andExpect(requestTo(URL + BeerClientImpl.GET_BEER_PATH))
                .andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));



        Page<BeerDTO> dtos = beerClient.listBeers();

        assertThat(dtos.getContent().size()).isGreaterThan(0);
    }

    BeerDTO getBeerDto() {
        return new BeerDTO().builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .price(new BigDecimal("12.95"))
                .quantityOnHand(500)
                .upc("123456")
                .build();
    }

    BeerDTOPageImpl getPage() {
        return new BeerDTOPageImpl(Arrays.asList(getBeerDto()));
    }


    @Test
    void testDeleteBeer() {
        BeerDTO newDTO = new BeerDTO().builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .price(new BigDecimal("12.95"))
                .quantityOnHand(500)
                .upc("123456")
                .build();

        BeerDTO savedDto = beerClient.createBeer(newDTO);

        beerClient.deleteBeer(savedDto.getId());

        assertThrows(HttpClientErrorException.class, () -> {
            beerClient.getBeerById(savedDto.getId());
        });
    }

    @Test
    void testUpdateBeer() {
        BeerDTO beerDTO = new BeerDTO().builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .price(new BigDecimal("12.95"))
                .quantityOnHand(500)
                .upc("123456")
                .build();

        BeerDTO savedDto = beerClient.createBeer(beerDTO);


        final String newName = "Mango Bobs v2";
        savedDto.setBeerName(newName);

        BeerDTO updatedBeer = beerClient.updateBeer(savedDto);

        assertEquals(newName, updatedBeer.getBeerName());
    }

    @Test
    void testCreateBeer() {
        BeerDTO beerDTO = new BeerDTO().builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .price(new BigDecimal("12.95"))
                .quantityOnHand(500)
                .upc("123456")
                .build();

        BeerDTO savedDto = beerClient.createBeer(beerDTO);
        assertNotNull(savedDto);

    }

    @Test
    void getByBeerId() {
        Page<BeerDTO> beerDTOS = beerClient.listBeers();

        BeerDTO dto = beerDTOS.getContent().get(0);

        BeerDTO byId = beerClient.getBeerById(dto.getId());

        assertNotNull(byId);
    }

    @Test
    void listBeersWithBeerName() {
        beerClient.listBeers("ALE", null, false, null, null);
    }
}