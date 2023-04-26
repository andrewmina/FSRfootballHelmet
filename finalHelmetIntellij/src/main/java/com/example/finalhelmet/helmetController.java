package com.example.finalhelmet;

import com.fazecast.jSerialComm.SerialPort;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class helmetController  {

//    @FXML
//    private Text leftHeavy;
//    @FXML
//    private Text rightHeavy;
//    @FXML
//    private Text frontHeavy;
//    @FXML
//    private Circle frontCircle;
//    @FXML
//    private Circle leftCircle;
//    @FXML
//    private Circle rightCircle;









    public void toggleVisibility(Circle theCircle) {
        theCircle.setVisible(true);
        theCircle.applyCss();
        //        theCircle.setFill(Color.RED);
        PauseTransition delay = new PauseTransition(Duration.seconds(2)); // create a new delay of 1 second
        if(theCircle != null) {
            delay.setOnFinished(event -> {
                System.out.println("Setting circle to invisible");
                theCircle.setVisible(false);
                System.out.println("Circle is now invisible");
                // code to be executed after the delay
                theCircle.getParent().requestLayout(); // update interface
            });
        }
        delay.play(); // start the delay
        theCircle.applyCss();
    }

    public void toggleVisibilityText(Text theText) {
        theText.setVisible(true);
        theText.applyCss();
        //        theText.setFill(Color.ALICEBLUE);
        PauseTransition delay = new PauseTransition(Duration.seconds(2)); // create a new delay of 1
        if (theText != null) {
            delay.setOnFinished(event -> {
                theText.setVisible(false);
                // code to be executed after the delay
                theText.getParent().requestLayout(); // update interface
            });
        }
        delay.play(); // start the delay
        theText.applyCss();
    }




}