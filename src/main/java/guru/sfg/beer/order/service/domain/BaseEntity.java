package guru.sfg.beer.order.service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

  public BaseEntity(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate) {
    this.id = id;
    this.version = version;
    this.createdDate = createdDate;
    this.lastModifiedDate = lastModifiedDate;
  }

  @Id
  @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
  @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
  @JdbcTypeCode(Types.VARCHAR)
  private UUID id;

  @Version private Long version;

  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp createdDate;

  @UpdateTimestamp private Timestamp lastModifiedDate;

  public boolean isNew() {
    return this.id == null;
  }
}
