package br.com.byteartworks.repositories;

import br.com.byteartworks.entities.Beer;
import br.com.byteartworks.enumeration.BeerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

/*
 *   @author: gabflbm. created on 09/07/2023 !
 */
@RepositoryRestResource(path = "beer", collectionResourceRel = "beer")
public interface BeerRepository extends JpaRepository<Beer, UUID> {

    Page<Beer> findAllByNameIsLikeIgnoreCase(String name, Pageable pageable);

    Page<Beer> findAllByType(BeerType type, Pageable pageable);

    Page<Beer> findAllByNameIsLikeIgnoreCaseAndType(String name, BeerType type, Pageable pageable);
}
