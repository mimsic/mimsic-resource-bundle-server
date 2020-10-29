package com.github.mimsic.rbs.rest.endpoint;

import com.github.mimsic.rbs.api.data.LanguagePack;
import com.github.mimsic.rbs.api.provider.ResourceBundleProvider;
import com.github.mimsic.rbs.rest.message.MessageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RbsController {

    @Autowired
    @Qualifier("LanguagePackProvider")
    private ResourceBundleProvider languagePackProvider;

    @RequestMapping(value = "/v1/language-pack", method = RequestMethod.GET, produces = "application/json")
    public MessageWrapper<LanguagePack<String>, String> languagePack(@RequestParam String language) {

        return new MessageWrapper<>(
                new LanguagePack<>(languagePackProvider.getStringNode(language), language),
                "Language-pack");
    }
}
