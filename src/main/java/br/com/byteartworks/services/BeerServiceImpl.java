package br.com.byteartworks.services;

import br.com.byteartworks.dto.BeerDTO;
import br.com.byteartworks.seed.BeerLoader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 14/06/2023 !
 */
@Slf4j
@AllArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerLoader beerLoader;

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        log.debug("Get Beer id: {} in service", id);

        return Optional.of(beerLoader.getBeerMap().get(id));
    }

    @Override
    public List<BeerDTO> getAllBeers() {
        return new ArrayList<>(beerLoader.getBeerMap().values());
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {

        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .name(beer.getName())
                .version(beer.getVersion())
                .type(beer.getType())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        beerLoader.getBeerMap().put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existing = beerLoader.getBeerMap().get(beerId);

        existing.setName(beer.getName());
        existing.setPrice(beer.getPrice());
        existing.setUpc(beer.getUpc());
        existing.setQuantityOnHand(beer.getQuantityOnHand());

        beerLoader.getBeerMap().put(existing.getId(), existing);
    }

    @Override
    public void deleteById(UUID beerId) {
        beerLoader.getBeerMap().remove(beerId);
    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO existing = beerLoader.getBeerMap().get(beerId);

        if (StringUtils.hasText(beer.getName())) {
            existing.setName(beer.getName());
        }

        if (beer.getType() != null) {
            existing.setType(beer.getType());
        }

        if (beer.getPrice() != null) {
            existing.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnHand() != null) {
           existing.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            existing.setUpc(beer.getUpc());
        }
    }
}
