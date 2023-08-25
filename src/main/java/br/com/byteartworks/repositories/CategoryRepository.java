package br.com.byteartworks.repositories;

import br.com.byteartworks.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
 *   @author: gabflbm. created on 25/08/2023 !
 */
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
