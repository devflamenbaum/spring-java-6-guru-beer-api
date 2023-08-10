package br.com.byteartworks.dto;

import br.com.byteartworks.enumeration.BeerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private BeerType type;

    @NotNull
    @NotBlank
    private String upc;
    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
