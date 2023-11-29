package ru.sds.straycats.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "price", schema = "straycats")
public class PriceEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("cat_id")
    private Long catId;

    @Column("price")
    private Double price;

    @Column("create_ts")
    private LocalDateTime createTs;
}
