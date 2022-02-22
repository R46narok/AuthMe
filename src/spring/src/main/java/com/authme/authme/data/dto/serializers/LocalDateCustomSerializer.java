package com.authme.authme.data.dto.serializers;

import com.authme.authme.data.dto.objects.ProfileEntryObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateCustomSerializer extends StdSerializer<LocalDate> {
    public LocalDateCustomSerializer() {
        this(null);
    }

    protected LocalDateCustomSerializer(Class<LocalDate> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("dateOfBirth", value.toString());
    }
}
