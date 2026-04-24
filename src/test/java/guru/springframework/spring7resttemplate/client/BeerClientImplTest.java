package guru.springframework.spring7resttemplate.client;

import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void listBeers() {
        beerClient.listBeers();
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