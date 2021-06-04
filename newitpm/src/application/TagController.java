package application;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TagController implements Initializable {


    @FXML
    private Label subname;

    @FXML
    private Label rtag;

    @FXML
    private Label subcode;

    @FXML
    private TextField subjectname;

    @FXML
    private TextField subjectcode;
    
    @FXML
    private Button tagsave;

    @FXML
    private Button tagclear;
    
    @FXML
    private ComboBox<String> relatedtagc;
    
    
    String query =null;
	ResultSet resultsset =null;
	PreparedStatement preparedStatement;
	Tags tags;
    

    
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
    
    @FXML
    public void Save(MouseEvent event) {
    	String subjectn = subjectname.getText();
		String subjectc =subjectcode.getText();
		String tagr =relatedtagc.getValue();
		
		
		if(subjectn.isEmpty() ||subjectc.isEmpty()||tagr.isEmpty()) {
			Alert alert =new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All Data");
			alert.showAndWait();
		}
		else {
			insertTag();
			
		}
		
		
		
		try {
			Parent root =FXMLLoader.load(getClass().getResource("ManageTag.fxml"));
			Scene scene = new Scene(root);
			Stage stage =new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	private void insertTag() {
		
		Connection conn =getConnection();
		String query ="insert into tags (subjectn,subjectc,tagr) values(?,?,?)";
		try {
			
		preparedStatement = getConnection().prepareStatement(query);
		preparedStatement.setString(1, subjectname.getText());
		preparedStatement.setString(2, subjectcode.getText());
		preparedStatement.setString(3, relatedtagc.getValue());
		
		preparedStatement.execute();
		
		JOptionPane.showMessageDialog(null, "Insert Successfully");
	}catch(SQLException ex) {
		JOptionPane.showMessageDialog(null, ex);
	}
			
		}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		 ObservableList<String> relatedtagList =
				  FXCollections.observableArrayList("Lecture","Tutorial","Lab");
		 relatedtagc.setItems(relatedtagList);
	}
	     
	
	  @FXML
	    void cleartag(ActionEvent event) {

		  subjectname.setText(" ");
		  subjectcode.setText(" ");
		  
		  
	    }
}



	
	

