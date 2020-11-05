package com.github.mimsic.rbs.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class RawStringSerializer extends StdSerializer<Object> {

    public RawStringSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object object, JsonGenerator generator, SerializerProvider provider) throws IOException {

        if (object instanceof String) {
            generator.writeRawValue((String) object);
        } else {
            generator.writeObject(object);
        }
    }
}
