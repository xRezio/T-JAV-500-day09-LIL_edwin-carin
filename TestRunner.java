import java.lang.reflect.Method;

public class TestRunner {
    public static void runTests(Class<?> testClass) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                Test testAnnotation = method.getAnnotation(Test.class);
                if (testAnnotation.enabled()) {
                    try {
                        System.out.println("Running test: " + testAnnotation.name());
                        method.invoke(testClass.getDeclaredConstructor().newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
