package com.example.foldermaneger;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;



public class OpenWindow {
    Stage stage = new Stage();

    public void OpenWindow(String url, String text, int sizeOne,int sizeTwo) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root,sizeOne,sizeTwo);
        stage.setTitle(text);
        stage.setScene(scene);
        stage.show();
    }
    public void CloseWindow(){
        stage.close();
    }
}
