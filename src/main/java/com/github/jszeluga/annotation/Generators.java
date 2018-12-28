package com.github.jszeluga.annotation;

import com.github.jszeluga.generators.Generator;

import javax.persistence.Entity;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to list the {@link Generator} and the order in which to process the {@link Entity}
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Generators {

    /**
     *
     * The list of {@link Generator} Classes
     */
    Class<? extends Generator>[] generators() default {};
}
