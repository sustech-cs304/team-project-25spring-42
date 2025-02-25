package sustech.cs304;

/**
 * Hello world!
 *
 */
// public class App 
// {
//     public static void main( String[] args )
//     {
//         System.out.println( "Hello World!" );
//     }
// }
import eu.mihosoft.monacofx.MonacoFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // create a new monaco editor node
        MonacoFX monacoFX = new MonacoFX();
        StackPane root = new StackPane(monacoFX);

        // set initial text
        monacoFX.getEditor().getDocument().setText(
                "#include <stdio.h>\n" +
                "int main() {\n" +
                "   // printf() displays the string inside quotation\n" +
                "   printf(\"Hello, World!\");\n" +
                "   return 0;\n" +
                "}");

        // use a predefined language like 'c'
        monacoFX.getEditor().setCurrentLanguage("c");
        monacoFX.getEditor().setCurrentTheme("vs-dark");

        // the usual scene & stage setup
        Scene scene = new Scene(root, 800,600);
        primaryStage.setTitle("MonacoFX Demo (running on JDK " + System.getProperty("java.version") + ")");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
