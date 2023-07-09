package br.com.byteartworks.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 09/07/2023 !
 */
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;
    private String customerName;

    @Version
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
