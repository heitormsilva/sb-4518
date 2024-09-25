package test.gtfs.model.gtfs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
@ToString
public final class Company implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@NotNull
	private final Long idCompany;

	@Column(nullable = false, unique = true)
	@NotBlank
	private final String name;

	@NotBlank
	private final String url;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Company company)) return false;

		return Objects.equals(getIdCompany(), company.getIdCompany());
	}

	@Override
	public int hashCode() {
		return 42;
	}
}
