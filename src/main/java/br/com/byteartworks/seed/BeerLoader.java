package br.com.byteartworks.seed;

import br.com.byteartworks.dto.BeerDTO;
import br.com.byteartworks.enumeration.BeerType;
import br.com.byteartworks.model.Beer;
import lombok.Getter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 15/06/2023 !
 */
@Getter
@Component
public class BeerLoader implements ApplicationRunner {

    Map<UUID, BeerDTO> beerMap;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID()).
                version(1)
                .name("Galaxy Cat")
                .type(BeerType.PALE_ALE)
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID()).
                version(1)
                .name("Crank")
                .type(BeerType.PALE_ALE)
                .upc("12348493")
                .price(new BigDecimal("10.99"))
                .quantityOnHand(352)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID()).
                version(1)
                .name("Sunshine City")
                .type(BeerType.IPA)
                .upc("45347237")
                .price(new BigDecimal("20.99"))
                .quantityOnHand(12)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);

    }
}
