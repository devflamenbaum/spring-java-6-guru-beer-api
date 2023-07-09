package br.com.byteartworks.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 09/07/2023 !
 */
@Builder
@Data
public class CustomerDTO {

    private UUID id;
    private String customerName;
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
