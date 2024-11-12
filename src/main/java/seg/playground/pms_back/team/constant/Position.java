package seg.playground.pms_back.team.constant;

import jakarta.persistence.Converter;
import lombok.Getter;
import seg.playground.pms_back.common.converter.EnumCode;
import seg.playground.pms_back.common.converter.EnumCodeConverter;

@Getter
public enum Position implements EnumCode<String> {
    VIEWER("VIEWER"),
    CONTRIBUTOR("CONTRIBUTOR"),
    PM("Project manager"),
    AD("ADMIN"),
    ;

    private final String value;

    Position(String value) {
        this.value = value;
    }

    @Converter
    static class Convert extends EnumCodeConverter<Position, String> {
        public Convert() {
            super(Position.class);
        }
    }
}
