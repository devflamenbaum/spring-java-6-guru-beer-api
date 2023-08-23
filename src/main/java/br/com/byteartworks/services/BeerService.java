package br.com.byteartworks.services;

import br.com.byteartworks.dto.BeerDTO;
import br.com.byteartworks.enumeration.BeerType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 14/06/2023 !
 */
public interface BeerService {

    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> getAllBeers(String beerName, BeerType beerType, Boolean showInventory);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
