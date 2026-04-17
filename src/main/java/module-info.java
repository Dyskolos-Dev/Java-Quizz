module com.example.javaquizz {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.javaquizz to javafx.fxml;
    exports com.example.javaquizz;
}