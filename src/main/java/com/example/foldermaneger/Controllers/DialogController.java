package com.example.foldermaneger.Controllers;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DialogController {
    String inputText;
    public String getInputText() {
        return inputText;
    }


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOK;



    @FXML
    private TextField textInput;
    public TextField getTextInput() {
        return textInput;
    }

    @FXML
    void btnCancelAction(ActionEvent event) {
        // Получаем ссылку на Stage текущего окна
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // Закрываем текущее окно
        stage.close();
    }

    @FXML
    void btnOKAction(ActionEvent event) {
        inputText=textInput.getText();
        System.out.println(inputText);
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // Закрываем текущее окно
        stage.close();
    }


    @FXML
    void initialize() {

    }

}
