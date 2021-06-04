package application;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

import java.net.URL;
import java.sql.*;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.io.IOException;

import javax.swing.JOptionPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ManageSessionController implements Initializable {

    int index = -1;

    @FXML
    private TableView<Session> managesessionTable;

    @FXML
    private TableColumn<Session, Integer> managedtableIDtable;

    @FXML
    private TableColumn<Session, String> lecturenameTable;

    @FXML
    private TableColumn<Session, String> subjectcodeIDtable;

    @FXML
    private TableColumn<Session, String> subjectnameTable;

    @FXML
    private TableColumn<Session, String> groupidTable;

    @FXML
    private TableColumn<Session, String> tagsTable;

    @FXML
    private TableColumn<Session, String> noOfstudentsTable;

    @FXML
    private TableColumn<Session, String> durationTable;
    
    @FXML
    private Button addparaSessionBtn;

    @FXML
    private Button addnoverSessionBtn;

  

    @FXML
    private Button addSessionBtn;


    @FXML
    private Button manageSessionDeleteBtn;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField lecturenameTxt;

    @FXML
    private TextField groupidTxt;

    @FXML
    private TextField subjectnameTxt;

    @FXML
    private TextField subjectcodeTxt;

    @FXML
    private TextField durationTxt;

    @FXML
    private TextField numofstudentTxt;

    @FXML
    private TextField tagsTxt;

    @FXML
    void addsessionbtnaction(ActionEvent event) {

    }


    String query =null;
    ResultSet resultsset =null;
    PreparedStatement preparedStatement;



    @FXML
    void tableSelect(MouseEvent event) {
        index = managesessionTable.getSelectionModel().getFocusedIndex();
        if(index <= -1) {
            return;
        }

        idTxt.setText(managedtableIDtable.getCellData(index).toString());
        lecturenameTxt.setText(lecturenameTable.getCellData(index).toString());
        subjectnameTxt.setText(subjectnameTable.getCellData(index).toString());
        subjectcodeTxt.setText(subjectcodeIDtable.getCellData(index).toString());
        groupidTxt.setText(groupidTable.getCellData(index).toString());
        tagsTxt.setText(tagsTable.getCellData(index).toString());
        numofstudentTxt.setText(noOfstudentsTable.getCellData(index).toString());
        durationTxt.setText(durationTable.getCellData(index).toString());

    }


    private void RefreshSessionTable (){

        ObservableList<Session>list = getsessionlist();

        managedtableIDtable.setCellValueFactory(new PropertyValueFactory<Session, Integer>("ID"));
        lecturenameTable.setCellValueFactory(new PropertyValueFactory<Session , String>("Lecturename"));
        subjectnameTable.setCellValueFactory(new PropertyValueFactory<Session , String>("Subjectname"));
        subjectcodeIDtable.setCellValueFactory(new PropertyValueFactory<Session , String>("Subjectcode"));
        groupidTable.setCellValueFactory(new PropertyValueFactory<Session , String>("GroupID"));
        tagsTable.setCellValueFactory(new PropertyValueFactory<Session , String>("Tags"));
        noOfstudentsTable.setCellValueFactory(new PropertyValueFactory<Session , String>("NumofStudents"));
        durationTable.setCellValueFactory(new PropertyValueFactory<Session,String>("Duration"));

        managesessionTable.setItems(list);

    }

    @FXML
    void addparasessionbtnaction(ActionEvent event) {
    	String id = idTxt.getText();
		String name =lecturenameTxt.getText();
		String code =subjectcodeTxt.getText();
		String sbname = subjectnameTxt.getText();
		String spid =groupidTxt.getText();
		String sptag =tagsTxt.getText();
		String spnum = numofstudentTxt.getText();
		String spd =durationTxt.getText();
		
		
		if(id.isEmpty() ||name.isEmpty()||code.isEmpty()) {
			Alert alert =new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All Data");
			alert.showAndWait();
		}
		else {
			insertGsession();
			
		}
		
		
		
		try {
			Parent root =FXMLLoader.load(getClass().getResource("SessionTab.fxml"));
			Scene scene = new Scene(root);
			Stage stage =new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML 
    void addnonsessionbtnaction(ActionEvent event){
    	String id = idTxt.getText();
		String name =lecturenameTxt.getText();
		String code =subjectcodeTxt.getText();
		String sbname = subjectnameTxt.getText();
		String spid =groupidTxt.getText();
		String sptag =tagsTxt.getText();
		String spnum = numofstudentTxt.getText();
		String spd =durationTxt.getText();
		
		
		if(id.isEmpty() ||name.isEmpty()||code.isEmpty()) {
			Alert alert =new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Fill All Data");
			alert.showAndWait();
		}
		else {
			insertNosession();
			
		}
		
		
		
		try {
			Parent root =FXMLLoader.load(getClass().getResource("SessionTab.fxml"));
			Scene scene = new Scene(root);
			Stage stage =new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }


	private void insertGsession() {
		
		Connection conn =getConnection();
		String query ="insert into parallel (pID,plecture,pscode,psubject,pgroupid,ptag,pns,pduration) values(?,?,?,?,?,?,?,?)";
		try {
			
		preparedStatement = getConnection().prepareStatement(query);
		preparedStatement.setString(1, idTxt.getText());
		preparedStatement.setString(2, lecturenameTxt.getText());
		preparedStatement.setString(3, subjectcodeTxt.getText());
		preparedStatement.setString(4, subjectnameTxt.getText());
		preparedStatement.setString(5, groupidTxt.getText());
		preparedStatement.setString(6, tagsTxt.getText());
		preparedStatement.setString(7, numofstudentTxt.getText());
		preparedStatement.setString(8, durationTxt.getText());
		
		preparedStatement.execute();
		
		JOptionPane.showMessageDialog(null, "Insert Successfully");
	}catch(SQLException ex) {
		JOptionPane.showMessageDialog(null, ex);
	}
		
	}
		private void insertNosession() {
			
			Connection conn =getConnection();
			String query ="insert into nonoverlap (nID,nlecture,nscode,nsubject,ngroupid,ntag,nns,nduration) values(?,?,?,?,?,?,?,?)";
			try {
				
			preparedStatement = getConnection().prepareStatement(query);
			preparedStatement.setString(1, idTxt.getText());
			preparedStatement.setString(2, lecturenameTxt.getText());
			preparedStatement.setString(3, subjectcodeTxt.getText());
			preparedStatement.setString(4, subjectnameTxt.getText());
			preparedStatement.setString(5, groupidTxt.getText());
			preparedStatement.setString(6, tagsTxt.getText());
			preparedStatement.setString(7, numofstudentTxt.getText());
			preparedStatement.setString(8, durationTxt.getText());
			
			preparedStatement.execute();
			
			JOptionPane.showMessageDialog(null, "Insert Successfully");
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
		}
    @FXML
    void update(ActionEvent event) {

        try {
            Connection con =getConnection();

            String idTableText = idTxt.getText();
            String lecturenameTableText = lecturenameTxt.getText();
            String subjectnameTableText = subjectnameTxt.getText();
            String subjectcodeTableText = subjectcodeTxt.getText();
            String groupidTableText= groupidTxt.getText();
            String tagsTableText = tagsTxt.getText();
            String numofstudentTableText = numofstudentTxt.getText();
            String durationTableText = durationTxt.getText();


//            System.out.println(idTableText);

            String query ="update managesessionm2 set Id='"+idTableText+"',lecturename='"+lecturenameTableText+"',subjectname ='"+subjectnameTableText+"',subjectcode ='"+subjectcodeTableText+"',groupid ='"+groupidTableText+"',tags ='"+tagsTableText+"',numofstudents ='"+numofstudentTableText+"',duration ='"+durationTableText+"'where Id='"+idTableText+"'";


            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.execute();

            JOptionPane.showMessageDialog(null, "Update Successfully");
            RefreshSessionTable();
        }catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }


    }


    public ObservableList<Session> getsessionlist() {

        ObservableList<Session> sessionlist = FXCollections.observableArrayList();
        Connection con = getConnection();
        String query = "SELECT * FROM managesessionm2";

        Statement st;
        ResultSet rs;

        try {

            st = con.createStatement();
            rs = st.executeQuery(query);
            Session session;
            while (rs.next()) {

                session = new Session(rs.getInt("Id"),rs.getString("lecturename"),rs.getString("subjectname"),rs.getString("subjectcode"),rs.getString("groupid"),rs.getString("tags"),rs.getString("numofstudents"),rs.getString("duration"));
                sessionlist.add(session);


            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return sessionlist;
    }

    public void showSession(){

        ObservableList<Session> list = getsessionlist();

        managedtableIDtable.setCellValueFactory(new PropertyValueFactory<Session, Integer>("ID"));
        lecturenameTable.setCellValueFactory(new PropertyValueFactory<Session , String>("Lecturename"));
        subjectnameTable.setCellValueFactory(new PropertyValueFactory<Session , String>("Subjectname"));
        subjectcodeIDtable.setCellValueFactory(new PropertyValueFactory<Session , String>("Subjectcode"));
        groupidTable.setCellValueFactory(new PropertyValueFactory<Session , String>("GroupID"));
        tagsTable.setCellValueFactory(new PropertyValueFactory<Session , String>("Tags"));
        noOfstudentsTable.setCellValueFactory(new PropertyValueFactory<Session , String>("NumofStudents"));
        durationTable.setCellValueFactory(new PropertyValueFactory<Session,String>("Duration"));

        managesessionTable.setItems(list);
    }



    public void initialize (URL arg0, ResourceBundle arg1){

        showSession();
        RefreshSessionTable();
    }




    public Connection getConnection() {
        Connection con;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itpm", "root","");
            return con;
        }catch(Exception e) {
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }


    @FXML
    void delete(ActionEvent event) {
        {
            Connection con =getConnection();
            String query ="delete from managesessionm2 where Id =?";
            try {
                preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, idTxt.getText());
                preparedStatement.execute();
                JOptionPane.showMessageDialog(null, "Deleted Successfully");
                RefreshSessionTable();
            }catch(SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }


        }

    }




}
