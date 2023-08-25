package br.com.byteartworks.services;

import br.com.byteartworks.dto.BeerDTO;
import br.com.byteartworks.enumeration.BeerType;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 14/06/2023 !
 */
public interface BeerService {

    Optional<BeerDTO> getBeerById(UUID id);

    Page<BeerDTO> getAllBeers(String beerName, BeerType beerType, Boolean showInventory, Integer pageNumber, Integer pageSize);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
