package org.example.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString
public class Product implements Entity {

    private long id;

    private String name;

    private BigDecimal price;

    private String category;

    private long userId;
}
