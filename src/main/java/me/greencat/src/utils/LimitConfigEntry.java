package me.greencat.src.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LimitConfigEntry {
    String max();
    String min();
    String value() default "dummyThing";
}
