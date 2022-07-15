package proc;

import ann.Handleable;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes("ann.Handleable")
public class HandleableProcessor extends AbstractProcessor {

    public HandleableProcessor() {

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(Handleable.class)) {
            if (e.getAnnotation(Handleable.class).sinceVersion().equals("DEV")) {
                System.out.println("start here");

                Handleable oldAnnotation = e.getAnnotation(Handleable.class);
                System.out.println("old value is " + oldAnnotation.sinceVersion());

                Object updatedValue = changeAnnotationValue(e.getAnnotation(Handleable.class), "sinceVersion", "3.0.0");

                System.out.println("updated value is " + updatedValue);

            }
        }
        return true;
    }

    public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue){
        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }
        memberValues.put(key,newValue);
        return newValue;
    }
}
