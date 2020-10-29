package com.github.mimsic.rbs.api.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.mimsic.rbs.api.json.StringRawSerializer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class LanguagePack<T> {

    @JsonSerialize(using = StringRawSerializer.class)
    private T content;

    private String language;
}
