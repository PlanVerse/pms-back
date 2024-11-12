package seg.playground.pms_back.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Objects;

@Converter
public abstract class EnumCodeConverter<T extends Enum<T> & EnumCode<E>, E> implements AttributeConverter<T, E> {

    private final Class<T> clazz;

    public EnumCodeConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute.getValue();
    }

    @Override
    public T convertToEntityAttribute(E dataBaseData) {
        if (Objects.isNull(dataBaseData)) {
            return null;
        }

        return Arrays.stream(clazz.getEnumConstants())
                .filter(e -> e.getValue().equals(dataBaseData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + dataBaseData));
    }
}