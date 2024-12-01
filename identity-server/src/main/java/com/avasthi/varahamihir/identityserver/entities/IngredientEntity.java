package com.avasthi.varahamihir.identityserver.entities;

import com.avasthi.varahamihir.identityserver.pojo.UnitConversion;
import com.avasthi.varahamihir.identityserver.pojo.UnitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ingredient")
public final class IngredientEntity extends AbstractBase {
    private String name;
    private String description;
    private String brand;
    private String url;
    private String[] tags;
    private UnitType unitType;
    private UnitConversion unitConversion;
}
