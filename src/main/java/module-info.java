module com.wisielec {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.wisielec to javafx.fxml;
    exports com.wisielec;
}