package com.baraka.domain;

import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkArgument;

public class Symbol extends Microtype<String> {

    private Symbol(String value) {
        super(value);
        checkArgument(!value.isBlank(), "Symbol cannot be empty");
    }

    public static Symbol symbol(String value) {
        return new Symbol(value);
    }
}
