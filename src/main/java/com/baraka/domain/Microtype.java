package com.baraka.domain;

import java.util.Objects;

public class Microtype<T> {

    private final T value;

    public Microtype(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Microtype<?> microtype = (Microtype<?>) o;
        return Objects.equals(value, microtype.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
