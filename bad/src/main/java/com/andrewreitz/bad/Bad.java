package com.andrewreitz.bad;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Target({
        CONSTRUCTOR,
        METHOD,
        FIELD,
        TYPE,
        LOCAL_VARIABLE
})
@Retention(SOURCE)
public @interface Bad {
}
