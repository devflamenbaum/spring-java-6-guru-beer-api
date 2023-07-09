package br.com.byteartworks.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 30/06/2023 !
 */
@Data
@Builder
public class Customer {

    private UUID id;
    private String customerName;
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
