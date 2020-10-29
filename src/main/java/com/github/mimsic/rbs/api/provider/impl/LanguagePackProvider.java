package com.github.mimsic.rbs.api.provider.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.mimsic.rbs.api.json.ObjectMapperUtil;
import com.github.mimsic.rbs.api.provider.ResourceBundleProvider;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

@Component("LanguagePackProvider")
public class LanguagePackProvider implements ResourceBundleProvider {

    public static final String BUNDLE_PACKAGE = "language-pack";

    private static final Map<String, JsonNode> JSON_NODE_MAP = new ConcurrentHashMap<>();
    private static final Map<String, String> JSON_STRING_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {

    }

    @Override
    public ResourceBundle getBundle(Locale locale) {

        return ResourceBundle.getBundle(BUNDLE_PACKAGE, locale);
    }

    @Override
    public JsonNode getJsonNode(String language) {

        return JSON_NODE_MAP.computeIfAbsent(language, this::jsonNode);
    }

    @Override
    public String getStringNode(String language) {

        return JSON_STRING_MAP.computeIfAbsent(language, this::jsonString);
    }


    private JsonNode jsonNode(String language) {

        return Optional.of(this.getBundle(new Locale(language)))
                .map((ResourceBundle bundle) -> {

                    ObjectNode node = ObjectMapperUtil.createObjectNode();
                    bundle.keySet().forEach((String key) -> node.put(key, bundle.getString(key)));
                    return node;
                })
                .orElse(null);
    }

    private String jsonString(String language) {

        return Optional.ofNullable(this.getJsonNode(language))
                .map((JsonNode node) -> {

                    try {
                        return ObjectMapperUtil.writeValueAsSting(node);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(null);
    }
}
