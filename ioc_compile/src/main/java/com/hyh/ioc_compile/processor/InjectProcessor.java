package com.hyh.ioc_compile.processor;

import com.google.auto.service.AutoService;
import com.hyh.annotation.InjectFragment;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class InjectProcessor extends AbstractProcessor {
    private Messager mMessager;
    private Elements mElementUtils;
    private Filer mFiler;
    private String packageName = "com.hyh.base_lib";

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
        String packageName = "";
        List<ClassName> factoryList = new ArrayList<>();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(InjectFragment.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                onError("Builder annotation can only be applied to class", element);
                return false;
            }

            packageName = mElementUtils.getPackageOf(element).getQualifiedName().toString();
            String elementName = element.getSimpleName().toString();
            ClassName builderClassName = ClassName.get(packageName, String.format("%sFactory", elementName));
            factoryList.add(builderClassName);

            TypeSpec typeSpec = createFactoryTypeSpec(element, builderClassName, elementName);

            JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                onError("Failed to write java file: " + e.getMessage(), element);
            }
        }

        if(!packageName.equals("")){
            ClassName repotityClassName = ClassName.get(packageName, "FragmentRepotity");
            TypeSpec typeSpec = createRepotityTypeSpec(repotityClassName,factoryList);
            JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
            }
        }
        return true;
    }

    private TypeSpec createFactoryTypeSpec(Element element, ClassName builderClassName, String elementName) {
        ClassName returnClass = ClassName.bestGuess(String.format("%s%s",packageName, ".BaseFragment"));
        MethodSpec createFragment = MethodSpec.methodBuilder("createFragment")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return new $T()", element)
                .returns(returnClass)
                .build();
        ClassName superClass = ClassName.bestGuess(String.format("%s%s",packageName, ".BaseFragmentFactory"));
        return TypeSpec
                .classBuilder(builderClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(createFragment)
                .superclass(superClass)
                .build();
    }

    private TypeSpec createRepotityTypeSpec(ClassName builderClassName,List<ClassName> factoryList) {
        ClassName list = ClassName.get("java.util", "List");
        ClassName arrayList = ClassName.get("java.util", "ArrayList");
        TypeName listType = ParameterizedTypeName.get(list, ClassName.get("java.lang","Class"));

        FieldSpec fieldSpec = FieldSpec.builder(listType, "sDataList")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .initializer("new $T()", arrayList)
                .build();

        CodeBlock.Builder blockBuilder = CodeBlock.builder();
        for(int i =0; i< factoryList.size();i++){
            blockBuilder.addStatement("sDataList.add($T.class)",factoryList.get(i));
        }

        return TypeSpec
                .classBuilder(builderClassName)
                .addModifiers(Modifier.PUBLIC)
                .addStaticBlock(blockBuilder.build())
                .addField(fieldSpec)
                .build();
    }

    private void onError(String message, Element element) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
