package com.example.myspring.beans.factory.annotation;

/**
 * This annotation may be used on a field or parameter as a qualifier for
 * candidate beans when autowiring. It may also be used to annotate other
 * custom annotations that can then in turn be used as qualifiers.
 * <p>
 *
 */
public @interface Qualifier {
    String value() default "";
}
