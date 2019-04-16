package com.hyh.ioc_compile.processor;

import com.google.auto.service.AutoService;
import com.hyh.annotation.InjectFragment;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class InjectProcessor extends AbstractProcessor {
    private Messager mMessager;
    private Elements mElementUtils;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<String>();
        annotationTypes.add(InjectFragment.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public boolean process(Set<? extends TypeElement> anotations, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(InjectFragment.class)) {
            if(element.getKind() != ElementKind.CLASS){
                return false;
            }
            String packageName = mElementUtils.getPackageOf(element).getQualifiedName().toString();
            String elementName = element.getSimpleName().toString();
            ClassName builderClassName = ClassName.get(packageName, String.format("%sFactory", elementName));

            TypeName elementType = TypeName.get(element.asType());
            MethodSpec createFragment = MethodSpec.methodBuilder("createFragment")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(elementType)
                    .addStatement("return new $T()", element)
                    .build();

            TypeSpec factory = TypeSpec.classBuilder(builderClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(createFragment)
                    .build();

            JavaFile javaFile = JavaFile.builder(packageName, factory).build();
            try {
            javaFile.writeTo(mFiler);
            } catch (IOException e) {
            }
        }

        return true;
    }
}
