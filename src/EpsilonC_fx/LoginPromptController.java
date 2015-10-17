/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EpsilonC_fx;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 *
 * @author Runnetty
 */
public class LoginPromptController implements Initializable {

    private String[] names = {"Drift","Moseng"};
    private String[] pass = {"Beta486","pass"};
    private User[] betaUsers = new User[names.length] ;

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Button UpdateButton;
    @FXML
    private Text cVersion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupBetaLogin();
        UpdateCheck u = new UpdateCheck();
        if (u.serverHasNewerVersion()) {           
            //Enable Update button and update the label
            //u.getUpdate();
            UpdateButton.setDisable(false);
            
        }else{
            UpdateButton.setDisable(true);
            FileHandler fh = new FileHandler();
            cVersion.setText("EpislonC Version: "+fh.readFile(new File(fh.getDefaultFilePath() 
                    +  fh.sep + "Patch" +fh.sep + "clientVersion.txt")));
        }
        
    }

    @FXML
    public void loginAttempt() {
        if(hasNameInList(usernameField.getText()) && hasPassInList(passwordField.getText()) ){
            System.out.println("OK Login In");
            Stage s = new Stage();
            startMainWindow(s);
        }else{
            JOptionPane.showMessageDialog(null, "Username or password is wrong!", 
                    "Warning", JOptionPane.PLAIN_MESSAGE);
        }
    }
    public void updateAttempt(){
        UpdateCheck u = new UpdateCheck();
        if (u.serverHasNewerVersion()) {
            u.getUpdate();
        }
    }

    public void startMainWindow(final Stage stage) {
        try {
            // load the scene fxml UI.
            // grabs the UI scenegraph view from the loader.
            // grabs the UI controller for the view from the loader.
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            final Parent root = (Parent) loader.load();
            final MainWindowController controller = loader.<MainWindowController>getController();

            TreeLoadingEventHandler treeLoadingEventHandler = new TreeLoadingEventHandler(controller);

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

            //scene.getStylesheets().add("css.css");
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Epsilon C");
            stage.setScene(scene);
            //stage.getIcons().add(new Image(getClass().getResourceAsStream("myIcon.png")));
            FileHandler fh = new FileHandler();
            File folder = new File(fh.getDefaultFilePath() + fh.sep + "NodeInfo");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Safety s = new Safety();
            s.generatekey();

            stage.show();
            Stage stage1 = (Stage) loginButton.getScene().getWindow();
            stage1.close();
        } catch (IOException ex) {
            Logger.getLogger(LoginPromptController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //BETALOGIN
    boolean hasNameInList(String name){
        for(int i = 0; i <betaUsers.length-1;i++){
            if(betaUsers[i].getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
    
    boolean hasPassInList(String pass){
        for(int i = 0; i <betaUsers.length-1;i++){
            if(betaUsers[i].getPassword().equalsIgnoreCase(pass)){
                return true;
            }
        }
        return false;
    }
    
    void setupBetaLogin(){
        for(int i = 0; i < names.length;i++){
            this.betaUsers[i] = new User(names[i],pass[i]);
        }
    }
}
