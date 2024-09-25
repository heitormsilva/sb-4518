package test.gtfs.model.gtfs.entity;

import jakarta.persistence.*;
import lombok.*;
import test.gtfs.model.gtfs.type.TransportationType;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Route extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "agency_id", nullable = false, foreignKey = @ForeignKey(name = "FK_route_agency"))
	@ToString.Exclude
	private Agency agency;

	@Column(nullable = false)
	private String routeLongName;

	@Column(nullable = false)
	private TransportationType routeType;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Route route)) return false;

		return Objects.equals(getId(), route.getId());
	}

	@Override
	public int hashCode() {
		return 42;
	}
}