package br.com.byteartworks.repositories;

import br.com.byteartworks.entities.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
 *   @author: gabflbm. created on 24/08/2023 !
 */
public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
