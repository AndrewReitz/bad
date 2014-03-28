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
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.LinkedHashSet;
import java.util.Set;

import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;
import static javax.tools.Diagnostic.Kind.WARNING;

public class BadProcessor extends AbstractProcessor {

  private Elements elementUtils;
  private Types typeUtils;

  private BadProcessorLogger logger;

  @Override public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);

    elementUtils = processingEnv.getElementUtils();
    typeUtils = processingEnv.getTypeUtils();

    logger = new BadProcessorLogger(processingEnv.getMessager());
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    Set<? extends Element> badAnnotatedElements = roundEnv.getElementsAnnotatedWith(Bad.class);
    for (Element badAnnotatedElement : badAnnotatedElements) {
      ElementKind kind = badAnnotatedElement.getKind();

      logger.d("Kind: " + kind);

      Element enclosingElement = badAnnotatedElement.getEnclosingElement();
      switch (kind) {
        case CLASS:
        case INTERFACE:
          logger.w(badAnnotatedElement.toString() + " contains some bad code. You may want to consider cleaning it up");
          break;
        case FIELD:
        case METHOD:
          logger.w("There is some bad code in " + enclosingElement.toString() + "#" + badAnnotatedElement.toString() + ", you may want to consider cleaning it up.");
          break;
        default:
          logger.w("There appears to be bad code here " + enclosingElement.toString() + "#" + badAnnotatedElement.toString());
      }
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
