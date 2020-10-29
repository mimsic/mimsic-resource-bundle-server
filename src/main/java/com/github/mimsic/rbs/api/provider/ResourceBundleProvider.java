package com.github.mimsic.rbs.api.provider;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Locale;
import java.util.ResourceBundle;

public interface ResourceBundleProvider {

    ResourceBundle getBundle(Locale locale);

    JsonNode getJsonNode(String language);

    String getStringNode(String language);
}
