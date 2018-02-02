package osama.me.vmannotation_processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
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
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import osama.me.vmannotation.BindFields;

public class VmAnnotationProcessor extends AbstractProcessor {

    private Messager messager;
    private Types typeUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
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

        String[] annotationArgs = method.getAnnotation(BindFields.class).viewIds();

        MethodSpec.Builder mainBuilder = MethodSpec.methodBuilder("bindfields")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(ClassName.get(variableAsElement.asType()), variable.getSimpleName().toString())
                .addParameter(ClassName.get("android.view", "View"), method.getAnnotation(BindFields.class).viewName());

        for (int i = 0; i < annotationArgs.length; i++) {
            mainBuilder.addStatement("(($T) $L.findViewById(R.id.$L)).setText($L.$L)",
                    ClassName.get("android.widget", "TextView"),
                    method.getAnnotation(BindFields.class).viewName(),
                    annotationArgs[i],
                    variable.getSimpleName(),
                    fieldsInArgument.get(i).getSimpleName());
        }

        TypeSpec bindFieldsType = TypeSpec.classBuilder(method.getEnclosingElement().getSimpleName() + "_BindFields")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(mainBuilder.build())
                .build();

        JavaFile javaFile = JavaFile.builder("osama.me.viewmodelannotations", bindFieldsType).build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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