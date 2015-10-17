package EpsilonC_fx;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

/**
 * 
 * Testy
 * @author Runnetty
 * 
 */

public class EpsilonC extends Application {
    
    protected String loggedInUser;
    
    
    @Override
    public void start(final Stage stage) throws Exception {
        // load the scene fxml UI.
        // grabs the UI scenegraph view from the loader.
        // grabs the UI controller for the view from the loader.
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPrompt.fxml"));
        final Parent root = (Parent) loader.load();
        final LoginPromptController controller = loader.<LoginPromptController>getController();
        controller.initialize(null, null);
        //TreeLoadingEventHandler treeLoadingEventHandler = new TreeLoadingEventHandler(controller);

        //Timer for security check
        /*final Timeline timeline = new Timeline(
         new KeyFrame(
         Duration.seconds(3),
         new SecurityCheckEventHandler()
         )
         );
         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
         */
        // initialize the stage.
        Scene scene = new Scene(root);
        scene.setFill(null);
        
        scene.getStylesheets().add("css.css");
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Login");
        stage.setScene(scene);
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("myIcon.png")));
        //generatekey();
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
