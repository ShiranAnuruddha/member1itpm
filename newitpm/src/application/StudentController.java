package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StudentController implements Initializable{


    @FXML
    private Button saveStu;

    @FXML
    private Button generateSID;

    @FXML
    private Button clearStu;

    @FXML
    private TextField GID;

    @FXML
    private TextField SGID;

    @FXML
    private ComboBox<String> academicyearsemesterc;

    @FXML
    private ComboBox<String> programmec;

    @FXML
    private ComboBox<String> groupnumberc;

    @FXML
    private ComboBox<String> subgroupnumberc;

    String query =null;
  	ResultSet resultsset =null;
  	PreparedStatement preparedStatement;
  	Tags tags;
    
    @FXML
    void clear(ActionEvent event) {
    	GID.setText(" ");
    	SGID.setText(" ");

    }

    @FXML
    void GenerateId(ActionEvent event) {
    	String academicYearSemesterS = academicyearsemesterc.getValue();
    	String programmeS = programmec.getValue();
    	String groupNoS = groupnumberc.getValue();
    	String sGroupNoS = subgroupnumberc.getValue();
    	String output1 = academicYearSemesterS+"."+programmeS+"."+groupNoS;
    	String output2 = academicYearSemesterS+"."+programmeS+"."+groupNoS+"."+sGroupNoS;
    

    	GID.setText(output1);
    	SGID.setText(output2);
    	Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText("ID generated");
		alert.showAndWait();
    }

    @FXML
    void Save(MouseEvent event) {
    	String ayrsem = academicyearsemesterc.getValue();
		String prog =programmec.getValue();
		String group =groupnumberc.getValue();
		String sgroup = subgroupnumberc.getValue();
		String groupid =GID.getText();
		String sgroupid =SGID.getText();
		
		if(ayrsem.isEmpty() ||prog.isEmpty()||group.isEmpty()||sgroup.isEmpty()||groupid.isEmpty()||sgroupid.isEmpty()) {
			Alert alert =new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All Data");
			alert.showAndWait();
		}
		else {
			insertStudent();
			
		}
		
		
		
		try {
			Parent root =FXMLLoader.load(getClass().getResource("ManageStudent.fxml"));
			Scene scene = new Scene(root);
			Stage stage =new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    	

    }

	private void insertStudent() {
		Connection conn =getConnection();
		String query ="insert into students (ayrsem,prog,ggroup,sgroup,groupid,sgroupid) values(?,?,?,?,?,?)";
		try {
			
		preparedStatement = getConnection().prepareStatement(query);
		preparedStatement.setString(1, academicyearsemesterc.getValue());
		preparedStatement.setString(2, programmec.getValue());
		preparedStatement.setString(3, groupnumberc.getValue());
		preparedStatement.setString(4, subgroupnumberc.getValue());
		preparedStatement.setString(5, GID.getText());
		preparedStatement.setString(6, SGID.getText());
		preparedStatement.execute();
		
		JOptionPane.showMessageDialog(null, "Insert Successfully");
	}catch(SQLException ex) {
		JOptionPane.showMessageDialog(null, ex);
	}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		        ObservableList<String> academicyearsemesterList =
				FXCollections.observableArrayList("Y1.S1","Y1.S2","Y2.S1","Y2.S2","Y3.S1","Y3.S2","Y4.S1","Y4.S2");
				academicyearsemesterc.setItems(academicyearsemesterList);
				  
				ObservableList<String> programmeList =
				FXCollections.observableArrayList("IT","SE","CS","DS","CSNE","ISE","IM");
				programmec.setItems(programmeList);
		
				ObservableList<String> groupnumberList =
				FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20");
				groupnumberc.setItems(groupnumberList);
						  
				ObservableList<String> subgroupnumberList =
				FXCollections.observableArrayList("01","02","03");
				subgroupnumberc.setItems(subgroupnumberList); }
	

public Connection getConnection() {
		
		Connection conn;
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/member","root","");
			return conn;
		}
		catch (Exception ex) {
			
			System.out.println("Error:"+ex.getMessage());
			return null;
		}
	}
    

    
}
