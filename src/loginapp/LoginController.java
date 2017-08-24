package loginapp;

import Admin.AdminController;
import Students.StudentController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// we are implementing Initializable so that we can access our fxml file
public class LoginController implements Initializable
{
    // here i created a instance of login model
    LoginModel loginModel =new LoginModel();
    @FXML
    private Label loginStatus;
    @FXML
    private Label dbstatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<option> combobox;
    @FXML
    private Button loginButton;
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        if (this.loginModel.isDatabaseConnected())
        {
            this.dbstatus.setText("Connected to Database");
        }
        else
        {
            this.dbstatus.setText("Not Connected to Database");
        }
        this.combobox.setItems(FXCollections.observableArrayList(option.values()));
    }
    @FXML
    public void Login(ActionEvent event)
    {
        try
        {
            if(this.loginModel.isLogin(this.username.getText(),this.password.getText(),((option)this.combobox.getValue()).toString()))
            {
                Stage stage=(Stage)this.loginButton.getScene().getWindow();
                stage.close();
                switch (((option)this.combobox.getValue()).toString())
                {
                    case "Admin":
                        adminLogin();
                        break;
                    case "Student":
                        studentLogin();
                        break;
                }
            }
            else
            {
                loginStatus.setText("Wrong Credentials");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void adminLogin()
    {
        try
        {
            Stage adminStage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            Pane adminroot =(Pane)loader.load(getClass().getResource("/Admin/admin.fxml").openStream());
            AdminController adminController=(AdminController)loader.getController();
            Scene scene=new Scene(adminroot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public  void studentLogin()
    {
        try
        {
            Stage userStage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            Pane root=(Pane)loader.load(getClass().getResource("/Students/students.fxml").openStream());
            StudentController studentController=(StudentController)loader.getController();
            Scene scene=new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Student Dashboard");
            userStage.setResizable(false);
            userStage.show();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
