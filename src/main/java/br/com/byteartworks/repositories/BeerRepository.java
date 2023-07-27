package br.com.byteartworks.repositories;

import br.com.byteartworks.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
 *   @author: gabflbm. created on 09/07/2023 !
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
