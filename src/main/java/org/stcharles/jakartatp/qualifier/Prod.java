package org.stcharles.jakartatp.qualifier;

import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Sert a différencier deux classes implémentant la même interface pour que le système d'injection puisse prendre le bonne objet.
 * Donc quand on veux utiliser une implémentation on ajoute cette annotation.
 */
@Qualifier
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface Prod {
}
