package test.gtfs.model.gtfs.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import test.gtfs.model.gtfs.type.TransportationType;

@Converter(autoApply = true)
public class TransportationTypeConverter implements AttributeConverter<TransportationType, Byte> {
	@Override
	public Byte convertToDatabaseColumn(TransportationType category) {
		if (category == null) {
			return null;
		}
		return category.getValue();
	}

	@Override
	public TransportationType convertToEntityAttribute(Byte value) {
		if (value == null) {
			return null;
		}
		return TransportationType.reverseLookup(value);
	}
}
