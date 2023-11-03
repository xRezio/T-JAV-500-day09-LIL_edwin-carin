public class Example {
    public static void main(String[] args) {
        Inspector<Number> inspector = new Inspector<>(Number.class);
        inspector.displayInformations();

        try {
            Number numberInstance = inspector.createInstance();
            System.out.println("Instance of Number created: " + numberInstance);
        } catch (ReflectiveOperationException e) {
            System.err.println("Error creating an instance of Number: " + e.getMessage());
        }
    }
}
