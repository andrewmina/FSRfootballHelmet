module com.example.finalhelmet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.fazecast.jSerialComm;
    requires java.datatransfer;

    opens com.example.finalhelmet to javafx.fxml;
    exports com.example.finalhelmet;
}