import ann.Handleable;
import proc.HandleableProcessor;

@Handleable(sinceVersion = "DEV")
public class Handler2 {
    public static void main(String[] args) {
        Handleable classAnnotation = Handler2.class.getAnnotation(Handleable.class);
        System.out.println("old ClassAnnotation = " + classAnnotation.sinceVersion());
        HandleableProcessor.changeAnnotationValue(classAnnotation, "sinceVersion", "3.0.0");
        System.out.println("new ClassAnnotation = " + classAnnotation.sinceVersion());
    }
}