package osama.me.vmannotation_processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import osama.me.vmannotation.BindFields;

import static javax.tools.Diagnostic.Kind.NOTE;
import static javax.tools.Diagnostic.Kind.OTHER;

public class VmAnnotationProcessor extends AbstractProcessor {

    private Messager messager;
    private Types typeUtils;
    private Filer filer;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindFields.class)) {
            if (element.getKind() != ElementKind.METHOD) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Can only be applied to Methods");
                return false;
            }

            List<? extends VariableElement> parameters = ((ExecutableElement) element).getParameters();
            for (VariableElement variable : parameters) {
                generateNewMethod((ExecutableElement) element, variable);
            }
        }
        return false;
    }

    private void generateNewMethod(final ExecutableElement method, final VariableElement variable) {

        Element variableAsElement = typeUtils.asElement(variable.asType());
        List<VariableElement> fieldsInArgument = ElementFilter.fieldsIn(variableAsElement.getEnclosedElements());

        int[] annotationArgs = method.getAnnotation(BindFields.class).viewIds();
        ArrayList<Integer> viewIds = new ArrayList<>();
        for (final int annotationArg : annotationArgs) {
            viewIds.add(annotationArg);
        }

        messager.printMessage(OTHER,
                String.format("modifiers: %s , returntype: %s, methodname: %s, argumentName:  %s, fieldsInArgument %s, annotation_args %s",
                        Arrays.deepToString(method.getModifiers().toArray()),
                        method.getReturnType().getKind().toString(),
                        method.getSimpleName().toString(),
                        variable.getSimpleName(),
                        Arrays.deepToString(fieldsInArgument.toArray()),
                        Arrays.deepToString(viewIds.toArray()))
        );

        MethodSpec main = MethodSpec.methodBuilder("bindfields")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(ClassName.get(variableAsElement.asType()), variable.getSimpleName().toString() )
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        messager.printMessage(NOTE, main.toString());

//        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
//                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                .addMethod(main)
//                .build();c
//
//        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
//                .build();
//
//        javaFile.writeTo(System.out);


    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> stringSet = new HashSet<>();
        stringSet.add(BindFields.class.getCanonicalName());
        return stringSet;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}