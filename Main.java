/**
 * The Main class serves as the entry point for the application. It contains the main method
 * responsible for starting the application by initializing the Viewer and invoking its
 * startApplication method.
 */
public class Main {

    /**
     * The main method, which serves as the entry point for the application.
     *
     * @param args The command-line arguments passed to the application (not used in this case).
     */
    public static void main(String[] args) {
        Viewer viewer = new Viewer();
        viewer.startApplication();
    }
}
