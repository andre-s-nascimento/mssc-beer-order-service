package guru.sfg.brewery.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {
  private UUID id;
  private Integer version;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:mm:ss.SSSSSSXXXXX", shape = Shape.STRING)
  private OffsetDateTime createdDate;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:mm:ss.SSSSSSXXXXX", shape = Shape.STRING)
  private OffsetDateTime lastModifiedDate;

  private String beerName;
  private String beerStyle;
  private String upc;
  private Integer quantityOnHand;

  @JsonFormat(shape = Shape.STRING)
  private BigDecimal price;
}
