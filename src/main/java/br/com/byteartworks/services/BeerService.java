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

    void updateBeerById(UUID beerId, BeerDTO beer);

    void deleteById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
