package com.example.foldermaneger.Controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CommandStrokeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnStartCommand;
    @FXML
    private Button btnExit;

    @FXML
    private TextField commandIn;

    @FXML
    private ListView<String > commandOut;

    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    void btnStartCommandAction(ActionEvent event) {
        try {
            list.clear();
            String process;
            Process command=Runtime.getRuntime().exec(commandIn.getText());
            BufferedReader input=new BufferedReader(new InputStreamReader(command.getInputStream()));
            process=input.readLine();
            while (process!=null){
                list.add(process);
                process=input.readLine();
            }
            input.close();
            commandOut.setItems(list);
        }catch (Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Неккоректный ввод", ButtonType.OK);
            alert.showAndWait();
        }

    }
    public void btnCleareAction(ActionEvent actionEvent) {
        commandIn.clear();
        list.clear();
        commandOut.setItems(list);
    }

    public void btnExitAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        // Закрываем текущее окно
        stage.close();
    }


    @FXML
    void initialize() {
    }

}
