package com.github.mimsic.rbs.api.provider.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.mimsic.rbs.api.json.ObjectMapperUtil;
import com.github.mimsic.rbs.api.provider.ResourceBundleProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LanguagePackProviderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagePackProviderTest.class);

    @Autowired
    @Qualifier("LanguagePackProvider")
    private ResourceBundleProvider provider;

    private ObjectNode enNode;
    private ObjectNode enUsNode;
    private ResourceBundle enBundle;
    private ResourceBundle enUsBundle;

    @Before
    public void setUp() throws Exception {

        enBundle = ResourceBundle.getBundle(LanguagePackProvider.BUNDLE_PACKAGE, new Locale("en"));
        enNode = ObjectMapperUtil.createObjectNode();
        enBundle.keySet().forEach((String key) -> enNode.put(key, enBundle.getString(key)));

        enUsBundle = ResourceBundle.getBundle(LanguagePackProvider.BUNDLE_PACKAGE, new Locale("en_US"));
        enUsNode = ObjectMapperUtil.createObjectNode();
        enUsBundle.keySet().forEach((String key) -> enUsNode.put(key, enUsBundle.getString(key)));
    }

    @Test
    public void getBundle() {

        ResourceBundle bundle = provider.getBundle(new Locale("en"));
        Assert.assertEquals(enBundle, bundle);
        Assert.assertEquals(enBundle.getString("language"), bundle.getString("language"));

        bundle = provider.getBundle(new Locale("en_US"));
        Assert.assertEquals(enUsBundle, bundle);
        Assert.assertEquals(enUsBundle.getString("language"), bundle.getString("language"));
    }

    @Test
    public void getJsonNode() {

        Assert.assertEquals(enNode, provider.getJsonNode("en"));
        Assert.assertEquals(enUsNode, provider.getJsonNode("en_US"));
    }

    @Test
    public void getStringNode() throws IOException {

        String stringNode = ObjectMapperUtil.writeValueAsSting(enNode);
        LOGGER.info("en StringNode: {}", stringNode);
        Assert.assertEquals(stringNode, provider.getStringNode("en"));

        stringNode = ObjectMapperUtil.writeValueAsSting(enUsNode);
        LOGGER.info("en_US StringNode: {}", stringNode);
        Assert.assertEquals(stringNode, provider.getStringNode("en_US"));
    }
}