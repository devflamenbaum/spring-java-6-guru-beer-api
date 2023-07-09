package br.com.byteartworks.dto;

import br.com.byteartworks.enumeration.BeerType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 09/07/2023 !
 */
@Builder
@Data
public class BeerDTO {

    private UUID id;
    private Integer version;
    private String name;
    private BeerType type;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
