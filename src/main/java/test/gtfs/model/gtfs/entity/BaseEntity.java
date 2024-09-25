package test.gtfs.model.gtfs.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@MappedSuperclass
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@SuppressWarnings("unused") // Auto generated value
	private Long id;
}
