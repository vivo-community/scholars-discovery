package edu.tamu.scholars.middleware.discovery.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface NestedObject {

    Reference[] properties() default {};

    boolean root() default true;

    @AliasFor("value")
    String label() default "label";

    @AliasFor("label")
    String value() default "label";

    @Documented
    @Target(FIELD)
    @Retention(RUNTIME)
    public @interface Reference {

        String value();

        String key();

    }

}
