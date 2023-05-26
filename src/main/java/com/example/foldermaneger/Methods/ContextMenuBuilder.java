package com.example.foldermaneger.Methods;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ContextMenuBuilder {
    public void setSelectedPaths(List<String> selectedPaths) {
        this.selectedPaths = selectedPaths;
    }

    List<String> selectedPaths = new ArrayList<>();

    /*public void setFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    File selectedFile;*/

    public void setEmptyChoose(Boolean emptyChoose) {
        this.emptyChoose = emptyChoose;
    }

    Boolean emptyChoose;



    public MethodList getMethod() {
        return methodList;
    }

    MethodList methodList=new MethodList();
    ContextMenu menu=new ContextMenu();
    Menu createItems=new Menu("Создать");
    MenuItem folderItem=new MenuItem("Папка");
    MenuItem fileItem=new MenuItem("Файл");
    MenuItem copyItem=new MenuItem("Копировать");
    MenuItem pasteItem=new MenuItem("Вставить");
    MenuItem cutItem=new MenuItem("Вырезать");
    MenuItem renameItem=new MenuItem("Переименовать");
    MenuItem deleteItem=new MenuItem("Удалить");
    MenuItem fullDeleteItem=new MenuItem("Полностью удалить");
    public ContextMenu createMenu(){
        //Scene scene = action.getScene();
        createItems.getItems().addAll(folderItem,fileItem);
        menu.getItems().addAll(createItems,copyItem,pasteItem,cutItem,renameItem,deleteItem,fullDeleteItem);
        createEvent();
        deleteEvent();
        otherEvent();
        return menu;

    }
    boolean copyStatus=false;
    boolean cutStatus=false;
    private void createEvent(){

        /*folderItem.setOnAction(actionEvent -> {
            String folderName;
            methodList.createDirectory(selectedFile);
        });
        fileItem.setOnAction(actionEvent -> {
            methodList.createFile(selectedFile);
        });*/

        folderItem.setOnAction(actionEvent -> {
            String folderName;
            methodList.createDirectory(selectedPaths);
        });
        fileItem.setOnAction(actionEvent -> {
            methodList.createFile(selectedPaths);
        });

    }
    private void deleteEvent(){
        /*deleteItem.setOnAction(actionEvent -> {
            methodList.deleteFile(selectedPaths);
        });*/
        fullDeleteItem.setOnAction(actionEvent -> {
            methodList.fullDelete(selectedPaths);
        });
    }

    private void otherEvent(){

        copyItem.setOnAction(actionEvent -> {
            methodList.copyFile(selectedPaths);
            copyStatus=true;
            cutStatus=false;
        });
        cutItem.setOnAction(actionEvent -> {
            methodList.copyFile(selectedPaths);
            cutStatus=true;
            copyStatus=false;
        });
        pasteItem.setOnAction(actionEvent -> {
            if (copyStatus){
                methodList.pasteFile(selectedPaths);

            } else if (cutStatus) {
                methodList.moveFile(selectedPaths);
                methodList.clearCopyFiles();
                cutStatus=false;
            }
        });
        renameItem.setOnAction(actionEvent -> {
            methodList.renameFile(selectedPaths);
        });
    }
}
