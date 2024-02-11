package com.avasthi.varahamihir.identityserver.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Ingredient extends AbstractBase {
    private String name;
    private String description;
    private String brand;
    private String url;
    private Quantity quantity;
}
