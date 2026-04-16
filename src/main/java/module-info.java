module com.example.javaquizz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javaquizz to javafx.fxml;
    exports com.example.javaquizz;
}