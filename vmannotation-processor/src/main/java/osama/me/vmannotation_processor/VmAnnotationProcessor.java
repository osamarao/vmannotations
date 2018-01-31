package osama.me.vmannotation_processor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import osama.me.vmannotation.BindFields;

import static javax.tools.Diagnostic.Kind.OTHER;

public class VmAnnotationProcessor extends AbstractProcessor {

    private Messager messager;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindFields.class)) {
            messager.printMessage(OTHER, element.getClass().getCanonicalName() + " : " + element.getKind());

            if (element.getKind() != ElementKind.METHOD) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Can only be applied to Methods");
                return false;
            }

            ExecutableElement ee = (ExecutableElement) element;
            List<? extends VariableElement> parameters = ee.getParameters();
            for (VariableElement ve : parameters) {
                Element asElement = typeUtils.asElement(ve.asType());
                List<VariableElement> fieldsInArgument = ElementFilter.fieldsIn(asElement.getEnclosedElements());
                writeToJavaFile(fieldsInArgument, ve.getSimpleName().toString());
            }
        }
        return false;
    }


    void writeToJavaFile(List<VariableElement> fieldsInArgument , String argumentName ){
        messager.printMessage(OTHER, String.format("argumentName:  %s, fieldsInArgument %s", argumentName, Arrays.deepToString(fieldsInArgument.toArray())));
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
