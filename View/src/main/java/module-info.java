module View {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires ModelProject;
    requires org.apache.logging.log4j;
    requires java.sql;

    opens pl.comp.java to javafx.fxml;
    exports pl.comp.java;
    exports Controllers;
    opens Controllers to javafx.fxml;
    exports AppBundle;
    opens AppBundle to javafx.fxml;
}