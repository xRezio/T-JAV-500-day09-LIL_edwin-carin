import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class Inspector<T> {
    private Class<T> inspectedClass;

    public Inspector(Class<T> inspectedClass) {
        this.inspectedClass = inspectedClass;
    }

    // This method will attempt to create a new instance of the inspected class using its default constructor.
    public T createInstance() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        return inspectedClass.getDeclaredConstructor().newInstance();
    }

    public void displayInformations() {
        // Display the name of the inspected class
        System.out.println("Information of the \"" + inspectedClass.getName() + "\" class:");
        // Display the superclass
        System.out.println("Superclass: " + inspectedClass.getSuperclass().getName());

        // Define the desired order of methods
        Map<String, Method> orderedMethods = new LinkedHashMap<>();
        String[] methodNames = {"byteValue", "shortValue", "intValue", "longValue", "floatValue", "doubleValue"};

        // Retrieve the declared methods and put them into the map maintaining the desired order
        for (String methodName : methodNames) {
            try {
                Method method = inspectedClass.getDeclaredMethod(methodName);
                orderedMethods.put(methodName, method);
            } catch (NoSuchMethodException e) {
                // This can happen if the class doesn't have one of the methods listed in methodNames
                e.printStackTrace();
            }
        }

        // Display the ordered methods
        System.out.println(orderedMethods.size() + " methods:");
        orderedMethods.forEach((name, method) -> System.out.println("- " + name));

        // Display the declared fields
        Field[] fields = inspectedClass.getDeclaredFields();
        System.out.println(fields.length + " fields:");
        for (Field field : fields) {
            System.out.println("- " + field.getName());
        }
    }
}
