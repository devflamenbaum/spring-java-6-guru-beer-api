package br.com.byteartworks.repositories;

import br.com.byteartworks.entities.Beer;
import br.com.byteartworks.enumeration.BeerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 09/07/2023 !
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {

    List<Beer> findAllByNameIsLikeIgnoreCase(String name);

    List<Beer> findAllByType(BeerType type);

    List<Beer> findAllByNameIsLikeIgnoreCaseAndType(String name, BeerType type);
}
