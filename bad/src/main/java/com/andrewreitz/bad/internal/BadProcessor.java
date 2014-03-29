/*
 * Copyright 2014 Andrew Reitz
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package com.andrewreitz.bad.internal;

import com.andrewreitz.bad.Bad;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.LinkedHashSet;
import java.util.Set;

import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;
import static javax.tools.Diagnostic.Kind.WARNING;

public class BadProcessor extends AbstractProcessor {

  private static final String CLASS_INTERFACE_ERROR_MESSAGE = "%s contains some bad code. You may want to consider cleaning it up.";
  private static final String METHOD_CONSTRUCTOR_FIELD_ERROR_MESSAGE = "There is some bad code in %s#%s, you may want to consider cleaning it up.";
  private static final String OTHER_ERROR_MESSAGE = "There appears to be bad code here %s#%s";

  private BadProcessorLogger logger;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    logger = new BadProcessorLogger(processingEnv.getMessager());
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    Set<? extends Element> badAnnotatedElements = roundEnv.getElementsAnnotatedWith(Bad.class);
    for (Element badAnnotatedElement : badAnnotatedElements) {
      ElementKind kind = badAnnotatedElement.getKind();
      Element enclosingElement = badAnnotatedElement.getEnclosingElement();
      String output;

      switch (kind) {
        case CLASS:
        case INTERFACE:
          output = String.format(CLASS_INTERFACE_ERROR_MESSAGE, badAnnotatedElement);
          break;
        case FIELD:
        case METHOD:
        case CONSTRUCTOR:
          output = String.format(METHOD_CONSTRUCTOR_FIELD_ERROR_MESSAGE, enclosingElement, badAnnotatedElement);
          break;
        default:
          output = String.format(OTHER_ERROR_MESSAGE, enclosingElement, badAnnotatedElement);
      }

      logger.w(output);
    }

    return true;
  }

  @Override public Set<String> getSupportedAnnotationTypes() {
    Set<String> supportTypes = new LinkedHashSet<>();
    supportTypes.add(Bad.class.getCanonicalName());
    return supportTypes;
  }

  @Override public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  static final class BadProcessorLogger {
    private final Messager messager;

    BadProcessorLogger(Messager messager) {
      this.messager = messager;
    }

    void d(String message) {
      messager.printMessage(NOTE, message);
    }

    void w(String message) {
      messager.printMessage(WARNING, message);
    }

    void e(String message) {
      messager.printMessage(ERROR, message);
    }
  }
}
