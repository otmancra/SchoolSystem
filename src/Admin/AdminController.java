package Admin;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable{
    @FXML
    private TextField id;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;

    @FXML
    private TableView<StudentData> studenttable;

    @FXML
    private TableColumn<StudentData,String > idcolumn;
    @FXML
    private TableColumn<StudentData,String > firstnamecolumn;
    @FXML
    private TableColumn<StudentData,String > lastnamecolumn;
    @FXML
    private TableColumn<StudentData,String > emailcolumn;
    @FXML
    private TableColumn<StudentData,String > dobcolumn;

    //lets make a global constructor to access db
    private dbConnection dc;
    private ObservableList<StudentData> data;
    private String sql= "SELECT * FROM students";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //here we are intializing database
        this.dc= new dbConnection();
    }
    @FXML
    private void loadStudentData(ActionEvent actionEvent)
    {
        try {
            Connection conn= dbConnection.getConnection();
            //this is object of Observable List
            this.data= FXCollections.observableArrayList();

            ResultSet rs= conn.createStatement().executeQuery(sql);
            //this is going to check it has something in the table or not
            while (rs.next())
            {
                this.data.add(new StudentData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Error "+ex);
        }
        //this keyword refers to the global variable
        //what we are doing here is filling data into our table
        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("ID"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("firstName"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("DOB"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.data);

    }

    @FXML
    private void addStudent(ActionEvent actionEvent)
    {
        String sqlInsert="INSERT INTO students(id,fname,lname,email,DOB) VALUES(?,?,?,?,?)";
        try {
            //here the name is same but it doesn't matter because this is a local variable
            Connection conn=dbConnection.getConnection();
            PreparedStatement statement=conn.prepareStatement(sqlInsert);
            statement.setString(1,this.id.getText());
            statement.setString(2,this.firstname.getText());
            statement.setString(3,this.lastname.getText());
            statement.setString(4,this.email.getText());
            statement.setString(5,this.dob.getEditor().getText());
            statement.execute();
            conn.close();




        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields(ActionEvent actionEvent)
    {
        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.email.setText("");
        this.dob.setValue(null);
    }

}
