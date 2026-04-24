package guru.springframework.spring7resttemplate.client;

import guru.springframework.spring7resttemplate.model.BeerDTO;
import guru.springframework.spring7resttemplate.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface BeerClient {

   Page<BeerDTO> listBeers();
   Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);
    BeerDTO getBeerById(BigDecimal price);
}
