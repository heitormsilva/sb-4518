package test.gtfs.model.gtfs.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Agency extends BaseEntity {

	@Column(nullable = false, unique = true)
	@NaturalId
	@EqualsAndHashCode.Include
	private String name;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false, length = 50)
	private String timezone;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agency", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private final List<Route> routes = new ArrayList<>();

	public void addRoute(Route route) {
		route.setAgency(this);
		routes.add(route);
	}

	public void removeRoute(Route route) {
		route.setAgency(null);
		routes.remove(route);
	}

	public List<Route> getRoutes() {
		return Collections.unmodifiableList(routes);
	}
}