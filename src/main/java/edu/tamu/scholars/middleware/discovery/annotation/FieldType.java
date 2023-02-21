package edu.tamu.scholars.middleware.discovery.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
public @interface FieldType {

    boolean readonly() default false;

    boolean stored() default true;

    boolean searchable() default true;

    String type() default "";

    String[] copyTo() default {};

    String defaultValue() default "";

    boolean required() default false;

    String name() default "";

    String value() default "";
}
