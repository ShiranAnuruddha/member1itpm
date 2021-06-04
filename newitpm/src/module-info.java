module newitpm {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires jdk.jdi;
	requires java.sql;
	requires java.desktop;
	requires mysql.connector.java;
	
	
	opens application to javafx.graphics,javafx.base,javafx.controls, javafx.fxml;
}
