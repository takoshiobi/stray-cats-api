package ru.sds.straycats.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cat", schema = "straycats")
public class CatEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("birth_date")
    private Date birth;

    @Column("breed")
    private String breed;

    @Column("gender")
    private Integer gender;

    @Column("removed_from_sale")
    private Boolean removedFromSale;
}
