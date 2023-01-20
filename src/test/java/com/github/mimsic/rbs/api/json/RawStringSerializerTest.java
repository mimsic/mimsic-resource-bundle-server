package com.github.mimsic.rbs.api.json;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.mimsic.rbs.api.data.LanguagePack;
import com.github.mimsic.rbs.api.provider.impl.LanguagePackProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Test the efficiency of raw string serializer by measuring elapsed time
 *
 * @author arz
 */
public class RawStringSerializerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RawStringSerializerTest.class);

    private ObjectNode enNode;
    private ResourceBundle enBundle;
    private String enString;
    private int iterations;

    @BeforeEach
    public void setUp() throws Exception {

        enBundle = ResourceBundle.getBundle(LanguagePackProvider.BUNDLE_PACKAGE, new Locale("en"));
        enNode = ObjectMapperUtil.createObjectNode();
        enBundle.keySet().forEach((String key) -> enNode.put(key, enBundle.getString(key)));
        enString = ObjectMapperUtil.writeValueAsSting(enNode);
        iterations = 1000;
    }

    /**
     * Test ObjectMapper serialization elapsed time with default serializer
     */
    @Test
    public void testDefaultSerializer() throws IOException {

        serialize((Integer iteration) -> new LanguagePack<>(enNode, enBundle.getLocale().getLanguage()), false);
    }

    /**
     * Test ObjectMapper serialization elapsed time with RawStringSerializer
     */
    @Test
    public void testRawStringSerializer() throws IOException {

        serialize((Integer iteration) -> new LanguagePack<>(enString, enBundle.getLocale().getLanguage()), false);
    }

    private <T> void serialize(Function<Integer, LanguagePack<T>> function, boolean log) throws IOException {

        long sum = 0;

        for (int i = 0; i < iterations; i++) {

            long start = System.nanoTime();
            LanguagePack<T> pack = function.apply(i);
            String result = ObjectMapperUtil.writeValueAsSting(pack);
            long stop = System.nanoTime();
            long time = stop - start;
            sum += time;
            if (log) {
                LOGGER.info("raw string serializer elapsed time: {}", time);
                LOGGER.info("raw string serializer result: {}", result);
            }
        }
        LOGGER.info("serializing average duration: {}", sum / iterations);
    }
}