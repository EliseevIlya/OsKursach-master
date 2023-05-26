package com.example.foldermaneger.Controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;


import com.example.foldermaneger.Methods.MethodsAdditionalInformation;
import com.example.foldermaneger.Methods.SearchFile;
import com.example.foldermaneger.OpenWindow;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {
    OpenWindow openWindow=new OpenWindow();
    MethodsAdditionalInformation method = new MethodsAdditionalInformation();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void btnExitAction(ActionEvent event) {
        Platform.exit();
    }
    @FXML
    void btnMenuTerminalAction(ActionEvent actionEvent) {
        try {
            openWindow.OpenWindow("CommandStroke.fxml","Командная строка",600,450);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnTestAction(ActionEvent actionEvent) {
        try {
            method.writeStorageInfoToFile("/home/ubuntu/Документы/test.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        /*fileTypeInfo.setCellValueFactory(param-> new SimpleStringProperty(param.getValue().getType().getName()));
        fileNameInfo.setCellValueFactory(param-> new SimpleStringProperty(param.getValue().getFileName()));

        fileSizeInfo.setCellValueFactory(param->new SimpleObjectProperty<>(param.getValue().getSize()));
        fileSizeInfo.setCellFactory(column->{
            return new TableCell<FileInfo,Long>(){
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item==null || empty){
                        setText(null);
                        setStyle("");
                    }else {
                        String text=String.format("%,d bytes",item);
                        if(item==-1L){
                            text="DIR";
                        }
                        setText(text);
                    }
                }
            };
        });

        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        fileDateInfo.setCellValueFactory(param->new SimpleStringProperty(param.getValue().getLastModefied().format(dtf)));

        filesTable.getSortOrder().add(fileTypeInfo);

        updateList(Paths.get("."));
    }

    public void updateList(Path path){

        try {
            filesTable.getItems().clear();
            filesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            filesTable.sort();
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.WARNING,"Данный путь некорректный", ButtonType.OK);
            alert.showAndWait();
        }
    }*/
    }


}
