package osama.me.vmannotation_processor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class VmAnnotationProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
    }

    @Override

    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.OTHER, "Osama");
        messager.printMessage(Diagnostic.Kind.OTHER, Arrays.deepToString(set.toArray()));
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> stringSet = new HashSet<>();
        stringSet.add("osama.me.vmannotation.BindFields");
        return stringSet;
    }
}
