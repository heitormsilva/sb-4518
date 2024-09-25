package test.gtfs.model.gtfs.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum TransportationType {
	BUS((byte) 3);

	@Getter
	private final Byte value;
	private static final Map<Byte, TransportationType> enumMap = Stream.of(values())
			.collect(Collectors.toMap(TransportationType::getValue, Function.identity()));

	public static TransportationType reverseLookup(Byte value) {
		var result = enumMap.get(value);
		if (result == null) {
			throw new IllegalArgumentException(
					String.format("'%s' has no corresponding value. Accepted values: %s", value, enumMap));
		}
		return result;
	}
}
