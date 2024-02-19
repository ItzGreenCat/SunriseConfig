package me.greencat.src.utils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigEntry {
        String value() default "dummyThing";
}
