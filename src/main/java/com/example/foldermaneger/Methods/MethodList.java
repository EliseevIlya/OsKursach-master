package com.example.foldermaneger.Methods;


import com.example.foldermaneger.Controllers.DialogController;
import com.example.foldermaneger.OpenWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MethodList {
    private OpenWindow openWindow=new OpenWindow();

    public List<String> getCopyFiles() {
        return copyFiles;
    }
    public void clearCopyFiles(){
        copyFiles.clear();
    }

    private List<String> copyFiles = new ArrayList<>();

    //Path root;
    Path root=new File(".").toPath();
    /*private File protect(File file){
        if (!Objects.isNull(file) && (file.toString().startsWith(new File(root+"/System").toString())
        ||file.toString().startsWith(new File(root+"/Корзина").toString())))
        {
            Alert alert=new Alert(Alert.AlertType.ERROR,"Нельзя удалиь сиситемные папки", ButtonType.OK);
            alert.showAndWait();
            throw new RuntimeException("Нельзя удалить системные папки");
        }else {
            return file;
        }
    }*/

    private Boolean protect(File file){
        if (!Objects.isNull(file) && (file.toString().startsWith(new File(root+"/System").toString())
                ||file.toString().startsWith(new File(root+"/Корзина").toString())))
        {
            Alert alert=new Alert(Alert.AlertType.ERROR,"Нельзя удалиь сиситемные папки", ButtonType.OK);
            alert.showAndWait();
            throw new RuntimeException("Нельзя удалить системные папки");
        }else {
            return true;
        }
    }

    public void setRoot(Path path){
        this.root=path;
    }
    public void moveFile(List<String>  destinationList){
        for (int i = 0; i < copyFiles.size(); i++) {
            File source=new File(copyFiles.get(i));
            if (protect(source)){
                String command = "mv " + source + " " + destinationList.get(0);

                // Создание процесса для выполнения команды в терминале
                ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);

                // Запуск процесса и ожидание завершения
                try {
                    Process process = processBuilder.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        //protect(source).renameTo(new File(destination.toString()+"/"+source.getName()));
    }
    public void deleteFile(File source){
        //moveFile(protect(source), new File(root+"/Корзина/"));
        System.out.println("test");
        System.out.println(source);
    }
    public void fullDelete(List<String> sourceList){
        for (int i = 0; i < sourceList.size(); i++) {
            File source= new File(sourceList.get(i));
            if(protect(source)){
                if(source.isDirectory()){
                    String command = "rm -r " + source;
                    System.out.println(command);

                    // Создание процесса для выполнения команды в терминале
                    ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);

                    // Запуск процесса и ожидание завершения
                    try {
                        Process process = processBuilder.start();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    String command = "rm " + source;
                    System.out.println(command);

                    // Создание процесса для выполнения команды в терминале
                    ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);

                    // Запуск процесса и ожидание завершения
                    try {
                        Process process = processBuilder.start();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

        }

    }
    public void createFile(List<String> source){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Создать файл");
        dialog.setHeaderText("Введите название файла с расширением");
        dialog.setContentText("Название:");



        dialog.showAndWait().ifPresent(fileName -> {
            /*String command = "touch " + source +"/" + fileName;
            System.out.println(command);

            // Создание процесса для выполнения команды в терминале
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);

            // Запуск процесса и ожидание завершения
            try {
                Process process = processBuilder.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/

            File newFile = new File(source.get(0) +"/"+ fileName);
            try {
                newFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        /*File file=new File(source.toString()+"/"+name);
        try {
            file.createNewFile();
        }catch (Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Не удалось удалить файл",ButtonType.OK);
            alert.showAndWait();
        }*/
    }
    public void createDirectory(List<String> source){
        //DialogController dialog =new DialogController();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Создать папку");
        dialog.setHeaderText("Введите название папки");
        dialog.setContentText("Название:");
        dialog.showAndWait().ifPresent(folderName -> {
            File newFolder = new File(source.get(0)+"/"+ folderName);
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }
        });


        /*try {
            openWindow.OpenWindow("Dialog.fxml","Создание директории",360,140);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(dialog.getInputText());
        File newDirectory=new File(protect(source).toString()+"/"+dialog.getInputText());*/
        /*if (newDirectory.mkdir()){
            newDirectory.getName();
        }*/

    }
    public void renameFile(List<String> source){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Создать файл");
        dialog.setHeaderText("Введите название файла с расширением");
        dialog.setContentText("Название:");

        dialog.showAndWait().ifPresent(fileName -> {
            /*String command = "touch " + source +"/" + fileName;
            System.out.println(command);

            // Создание процесса для выполнения команды в терминале
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);

            // Запуск процесса и ожидание завершения
            try {
                Process process = processBuilder.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/

            File newFile = new File(source.get(0) +File.separator+ fileName);
            try {
                newFile.renameTo(newFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        /*DialogController dialog=new DialogController();
        try {
            openWindow.OpenWindow("Dialog.fxml","Переименовать",360,140);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String newName=dialog.getInputText();
        if (!Objects.isNull(newName)){
            if(!protect(source).renameTo(new File(source.getParent()+"/"+newName))){
                Alert alert=new Alert(Alert.AlertType.ERROR,"Не удалось переименовать",ButtonType.OK);
                alert.showAndWait();
            }
        }*/
    }
    public void copyFile(List<String> sourceList){
        if (!copyFiles.isEmpty()){
            copyFiles.clear();
        }
        copyFiles=sourceList;
    }
    public void pasteFile(List<String> destinationList){
        for (int i = 0; i < copyFiles.size(); i++) {
            File source= new File(copyFiles.get(i));
            if (protect(source)){
                String command = "cp -r " + source + " " + destinationList.get(0);

                // Создание процесса для выполнения команды в терминале
                ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);

                // Запуск процесса и ожидание завершения
                try {
                    Process process = processBuilder.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
