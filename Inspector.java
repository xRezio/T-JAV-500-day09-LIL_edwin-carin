import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Inspector<T> {
    private Class<T> inspectedClass;

    public Inspector(Class<T> inspectedClass) {
        this.inspectedClass = inspectedClass;
    }

    public void displayInformations() {
        System.out.println("Information of the “" + inspectedClass.getName() + "” class:");
        System.out.println("Superclass: " + inspectedClass.getSuperclass().getName());

        // Display only the declared methods of the class
        Method[] methods = inspectedClass.getDeclaredMethods();
        System.out.println(methods.length + " methods:");
        Arrays.stream(methods)
              .filter(method -> !Modifier.isStatic(method.getModifiers())) // Filter out static methods
              .forEach(method -> System.out.println("- " + method.getName()));

        // Display only the declared fields of the class
        Field[] fields = inspectedClass.getDeclaredFields();
        System.out.println(fields.length + " fields:");
        Arrays.stream(fields)
              .filter(field -> !Modifier.isStatic(field.getModifiers())) // Filter out static fields
              .forEach(field -> System.out.println("- " + field.getName()));
    }

    public T createInstance() throws ReflectiveOperationException {
        return inspectedClass.getDeclaredConstructor().newInstance();
    }
}
