package com.example.finalhelmet;


import com.fazecast.jSerialComm.SerialPort;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class helmetApplication extends Application {




    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("helmetFXML.fxml"));
        Parent root = fxmlLoader.load();

        TabPane myPane = (TabPane) root.lookup("#myPane");
        Circle leftCircle = (Circle) myPane.lookup("#leftCircle");
        Circle frontCircle = (Circle) myPane.lookup("#frontCircle");
        Circle rightCircle = (Circle) myPane.lookup("#rightCircle");
        Text rightHeavy = (Text) myPane.lookup("#rightHeavy");
        Text leftHeavy = (Text) myPane.lookup("#leftHeavy");
        Text frontHeavy = (Text) myPane.lookup("#frontHeavy");
        Text rightMild = (Text) myPane.lookup("#rightMild");
        Text leftMild = (Text) myPane.lookup("#leftMild");
        Text frontMild = (Text) myPane.lookup("#frontMild");

        ListView<String> hitList = (ListView<String>) root.lookup("#hitList");
        rightMild.setVisible(false);
        frontMild.setVisible(false);
        leftMild.setVisible(false);
        rightHeavy.setVisible(false);
        leftHeavy.setVisible(false);
        frontHeavy.setVisible(false);
        frontCircle.setVisible(false);
        frontCircle.applyCss();
        leftCircle.setVisible(false);
        leftCircle.applyCss();
        rightCircle.setVisible(false);
        rightCircle.applyCss();
        ObservableList<String> hits = FXCollections.observableArrayList();

        Tab introTab = null;
        for (Tab tab : myPane.getTabs()) {
            if (tab.getId().equals("introTab")) {
                introTab = tab;
                break;
            }
        }

        if (introTab != null) {
            myPane.getSelectionModel().select(introTab);
        } else {
            System.out.println("Unable to find introTab in FXML file.");
        }

        Scene scene = new Scene(myPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            SerialPort serialPort = SerialPort.getCommPort("COM3"); // Replace with the port name where the Arduino is connected
            serialPort.openPort();
            serialPort.setBaudRate(115200); // Assuming that the Arduino is transmitting data at this baud rate
            System.out.println("starting com");


            // Read data from serial port
            String serialData = "";
            while (true) {

//                hitList.setItems(hits);
//                System.out.println(hits);
                byte[] buffer = new byte[20]; // Define buffer size according to your needs
                int bytesRead = serialPort.readBytes(buffer, buffer.length);
                serialData += new String(buffer, 0, bytesRead);

                // Split data into lines
                int lineEnd = serialData.indexOf("\r\n");
                while (lineEnd >= 0) {

                    String line = serialData.substring(0, lineEnd);
                    serialData = serialData.substring(lineEnd + 2);

                    String[] values = line.trim().split(" ", 5);
                    int analogValue1 = Integer.parseInt(values[0]);
                    int analogValue2 = Integer.parseInt(values[1]);
                    int analogValue3 = Integer.parseInt(values[2]);
                    int analogValue4 = Integer.parseInt(values[3]);
                    int analogValue5 = Integer.parseInt(values[4]);


                    // Do something with the analog values...

                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            if (analogValue1 > 700 || analogValue5 > 700) { // 1 is on the four head
                                System.out.println("Heavy Hit detected on left!");
                                leftCircle.setFill(Color.RED);
                                leftCircle.setVisible(true);
                                leftHeavy.setVisible(true);
                                hits.add("Heavy Hit detected on left!");
                                hitList.setItems(hits);
                                Thread.sleep(3000);
                                leftCircle.setVisible(false);
                                leftHeavy.setVisible(false);


//                                toggleVisibility(leftCircle);
//                                toggleVisibilityText(leftHeavy);

                            } else if (analogValue2 > 700 || analogValue4 > 700) { // 4 near the front and 2 near the back
                                rightCircle.setFill(Color.RED);
                                rightCircle.setVisible(true);
                                rightHeavy.setVisible(true);
                                hits.add("Heavy hit detected on the right region of the helmet");
                                hitList.setItems(hits);
                                Thread.sleep(3000);
                                rightCircle.setVisible(false);
                                rightHeavy.setVisible(false);

                                System.out.println("Heavy hit detected on the right region of the helmet");
                            } else if (analogValue3 > 700) { // 1 is on the four head

                                System.out.println("Heavy Hit detected head on!");
                                frontCircle.setFill(Color.RED);
                                frontCircle.setVisible(true);
                                frontMild.setVisible(true);
                                hits.add("Heavy Hit detected head on!");
                                hitList.setItems(hits);
                                Thread.sleep(3000);
                                frontCircle.setVisible(false);
                                frontMild.setVisible(false);



                            }else if (analogValue1 > 500 || analogValue5 > 500) { // 1 is on the four head
                                System.out.println("Heavy Hit detected on left!");
                                leftCircle.setFill(Color.YELLOW);
                                leftCircle.setVisible(true);
                                leftMild.setVisible(true);
                                hits.add("Heavy Hit detected on left!");
                                hitList.setItems(hits);
                                Thread.sleep(3000);
                                leftCircle.setVisible(false);
                                leftMild.setVisible(false);


//                                toggleVisibility(leftCircle);
//                                toggleVisibilityText(leftHeavy);

                            } else if (analogValue2 > 500 || analogValue4 > 500) { // 4 near the front and 2 near the back
                                rightCircle.setVisible(true);
                                rightMild.setVisible(true);
                                hits.add("Heavy hit detected on the right region of the helmet");
                                hitList.setItems(hits);
                                Thread.sleep(3000);
                                rightCircle.setVisible(false);
                                rightMild.setVisible(false);

                                System.out.println("Heavy hit detected on the right region of the helmet");
                            } else if (analogValue3 > 500) { // 1 is on the four head
                                frontCircle.setFill(Color.YELLOW);
                                System.out.println("Heavy Hit detected head on!");
                                frontCircle.setVisible(true);
                                frontMild.setVisible(true);
                                hits.add("Heavy Hit detected head on!");
                                hitList.setItems(hits);
                                Thread.sleep(3000);
                                frontCircle.setVisible(false);
                                frontMild.setVisible(false);



                            }
                            return null;
                            
                        }
                    };
                    Thread.sleep(100);
                    Thread thread = new Thread(task);
                    thread.setDaemon(true); // optional, sets the thread as a daemon thread
                    thread.start();

                    System.out.println(analogValue1 + " " + analogValue2 + " " + analogValue3 + " " + analogValue4 + " " + analogValue5);
//                            if (analogValue1 > 500 || analogValue5 > 500) {  // 5 near the front 1 near the  back
//                                System.out.println("Heavy hit detected on the left region of the helmet");
//                        Platform.runLater(() -> {
//                                leftCircle.setVisible(true);
//                                toggleVisibility(leftCircle);
//                                toggleVisibilityText(leftHeavy);
//                        });
//
//                            } else if (analogValue3 > 500) { // 1 is on the four head
//                        Platform.runLater(() -> {
//                                System.out.println("Heavy Hit detected head on!");
//                                frontCircle.setVisible(true);
//                                toggleVisibility(frontCircle);
//                                toggleVisibilityText(frontHeavy);
//                        });
//
//                            } else if (analogValue2 > 500 || analogValue4 > 500) { // 4 near the front and 2 near the back
////                        Platform.runLater(() -> {
//                                rightCircle.setVisible(true);
//                                toggleVisibility(rightCircle);
//                                toggleVisibilityText(rightHeavy);
////                        });
//                                System.out.println("Heavy hit detected on the right region of the helmet");
//                            }
//
                    lineEnd = serialData.indexOf("\r\n");
                }

//                try {
//                    Thread.sleep(10); // Add some delay to allow for more data to be received
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
//                });

        });


    };

    public static void main(String[] args) {
        launch(args);
    }
}
