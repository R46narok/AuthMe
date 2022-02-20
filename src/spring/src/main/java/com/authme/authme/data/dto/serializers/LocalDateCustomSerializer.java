package com.authme.authme.data.dto.serializers;

import com.authme.authme.data.dto.objects.ProfileEntryObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateCustomSerializer extends StdSerializer<ProfileEntryObject<LocalDate>> {
    public LocalDateCustomSerializer() {
        this(null);
    }

    protected LocalDateCustomSerializer(Class<ProfileEntryObject<LocalDate>> t) {
        super(t);
    }

    @Override
    public void serialize(ProfileEntryObject<LocalDate> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        if(value.getValue() != null)
            gen.writeStringField("value", value.getValue().toString());
        else
            gen.writeStringField("value", null);
        gen.writeBooleanField("validated", value.getValidated());
        if(value.getLastUpdated() != null)
            gen.writeStringField("lastUpdated", value.getLastUpdated().toString());
        else
            gen.writeStringField("lastUpdated", null);
        gen.writeEndObject();
    }
}
