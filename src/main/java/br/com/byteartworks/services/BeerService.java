package br.com.byteartworks.services;

import br.com.byteartworks.dto.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 14/06/2023 !
 */
public interface BeerService {

    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> getAllBeers();

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    Boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
