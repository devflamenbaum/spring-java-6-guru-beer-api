package br.com.byteartworks.controllers;

import br.com.byteartworks.dto.BeerDTO;
import br.com.byteartworks.entities.Beer;
import br.com.byteartworks.exceptions.NotFoundException;
import br.com.byteartworks.repositories.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/*
 *   @author: gabflbm. created on 19/07/2023 !
 */
@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testBeerNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getBeerById(beer.getId());

        assertThat(dto.getName()).isEqualTo(beer.getName());
    }

    @Test
    void testListBeers() {
        List<BeerDTO> dtos = beerController.getAllBeers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.getAllBeers();

        assertThat(dtos.size()).isEqualTo(0);
    }
}