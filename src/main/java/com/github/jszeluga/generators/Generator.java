package com.github.jszeluga.generators;

import java.util.function.Consumer;

public interface Generator<T> extends Consumer<T> {

    void initialize() throws Exception;
}
