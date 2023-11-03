import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void runTests(Class<?> testClass) {
        // Create a list to hold methods that need to be run once per class
        List<Method> beforeClassMethods = new ArrayList<>();
        List<Method> afterClassMethods = new ArrayList<>();

        // Create a list to hold methods that need to be run before and after each test
        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();

        // Classify methods by their annotations
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeClass.class)) {
                beforeClassMethods.add(method);
            } else if (method.isAnnotationPresent(AfterClass.class)) {
                afterClassMethods.add(method);
            } else if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }

        try {
            // Execute BeforeClass methods
            for (Method beforeClass : beforeClassMethods) {
                beforeClass.setAccessible(true);
                beforeClass.invoke(null); // static method, no instance required
            }

            // Main test loop
            for (Method method : testClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    Test testAnnotation = method.getAnnotation(Test.class);
                    if (testAnnotation.enabled()) {
                        Object instance = testClass.getDeclaredConstructor().newInstance();

                        // Execute Before methods
                        for (Method before : beforeMethods) {
                            before.setAccessible(true);
                            before.invoke(instance);
                        }

                        // Execute the test
                        System.out.println("Running test: " + testAnnotation.name());
                        method.setAccessible(true);
                        method.invoke(instance);

                        // Execute After methods
                        for (Method after : afterMethods) {
                            after.setAccessible(true);
                            after.invoke(instance);
                        }
                    }
                }
            }

            // Execute AfterClass methods
            for (Method afterClass : afterClassMethods) {
                afterClass.setAccessible(true);
                afterClass.invoke(null); // static method, no instance required
            }

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
