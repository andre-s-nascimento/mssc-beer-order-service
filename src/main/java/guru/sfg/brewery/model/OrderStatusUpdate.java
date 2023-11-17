package guru.sfg.brewery.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class OrderStatusUpdate  {

  @JsonProperty("id")
  private UUID id = null;

  @JsonProperty("version")
  private Integer version = null;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
  @JsonProperty("createdDate")
  private OffsetDateTime createdDate = null;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
  @JsonProperty("lastModifiedDate")
  private OffsetDateTime lastModifiedDate = null;

  private UUID orderId;
  private String customerRef;
  private String orderStatus;
}
