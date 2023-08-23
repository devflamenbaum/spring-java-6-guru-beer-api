package br.com.byteartworks.services;

import br.com.byteartworks.dto.BeerDTO;
import br.com.byteartworks.entities.Beer;
import br.com.byteartworks.enumeration.BeerType;
import br.com.byteartworks.mappers.BeerMapper;
import br.com.byteartworks.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/*
 *   @author: gabflbm. created on 19/07/2023 !
 */
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public List<BeerDTO> getAllBeers(String beerName, BeerType beerType, Boolean showInventory) {

        List<Beer> beerList;

        if(StringUtils.hasText(beerName)) {
            beerList = listBeersByName(beerName);
        } else if(!StringUtils.hasText(beerName) && beerType != null) {
            beerList = listBeersByType(beerType);
        } else if(StringUtils.hasText(beerName) && beerType != null) {
            beerList = listBeersByNameAndType(beerName, beerType);
        } else {
            beerList = beerRepository.findAll();
        }

        if (showInventory != null && !showInventory) {
            beerList.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerList
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    private List<Beer> listBeersByNameAndType(String beerName, BeerType beerType) {
        return beerRepository.findAllByNameIsLikeIgnoreCaseAndType("%" + beerName + "%", beerType);
    }

    private List<Beer> listBeersByType(BeerType beerType) {
        return beerRepository.findAllByType(beerType);
    }

    public List<Beer> listBeersByName(String beerName) {
        return beerRepository.findAllByNameIsLikeIgnoreCase("%" + beerName + "%");
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setName(beer.getName());
            foundBeer.setType(beer.getType());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setUpc(beer.getUpc());
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        if(beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            if (StringUtils.hasText(beer.getName())){
                foundBeer.setName(beer.getName());
            }
            if (beer.getType() != null){
                foundBeer.setType(beer.getType());
            }
            if (StringUtils.hasText(beer.getUpc())){
                foundBeer.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null){
                foundBeer.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null){
                foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
