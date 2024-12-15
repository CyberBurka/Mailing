module com.example.mailing {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.mail;


    opens com.example.mailing to javafx.fxml;
    exports com.example.mailing;
}