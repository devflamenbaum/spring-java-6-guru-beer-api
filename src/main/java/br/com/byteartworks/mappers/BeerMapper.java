package br.com.byteartworks.mappers;

import br.com.byteartworks.dto.BeerDTO;
import br.com.byteartworks.entities.Beer;
import org.mapstruct.Mapper;

/*
 *   @author: gabflbm. created on 19/07/2023 !
 */
@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
