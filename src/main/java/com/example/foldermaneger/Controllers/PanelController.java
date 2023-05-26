package com.example.foldermaneger.Controllers;

import com.example.foldermaneger.Methods.ContextMenuBuilder;
import com.example.foldermaneger.Methods.FileInfo;
import com.example.foldermaneger.Methods.MethodList;
import com.example.foldermaneger.Methods.SearchFile;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class PanelController {
    @FXML
    private TableView<FileInfo> filesTable;
    @FXML
    private TableColumn<FileInfo, String> fileTypeInfo;
    @FXML
    private TableColumn<FileInfo, String> fileNameInfo;
    @FXML
    private TableColumn<FileInfo, Long> fileSizeInfo;
    @FXML
    private TableColumn<FileInfo, String> fileDateInfo;
    @FXML
    private ComboBox<String> disksBox;
    @FXML
    private TextField pathField;
    @FXML
    private TextField searchField;
    @FXML
    private Button btnRefresh;
    @FXML
    private TreeView<FileInfo> treeViewPaths;

    Path root=new File(".").toPath();
    ContextMenuBuilder factory=new ContextMenuBuilder();
    MethodList methodList=new MethodList();
    Boolean searchStatus = false;



    private ArrayList<Path> pathHistory =new ArrayList<>();
    private int pathHistoryI=0;
    private boolean isBack=false;

    Desktop dt = Desktop.getDesktop();
    @FXML
    public void btnSearchAction(ActionEvent actionEvent) {
        SearchFile searchFile =new SearchFile();
        searchUpdateList(searchFile.searchFiles(Path.of(pathField.getText()),searchField.getText()));
        searchStatus=true;
    }

    @FXML
    void btnBackAction(ActionEvent event) {
        try {
            if (pathHistoryI!=0){
                pathHistoryI-=1;
            }
            updateList(pathHistory.get(pathHistoryI));
            System.out.println(pathHistoryI);
            isBack=true;
        }catch (RuntimeException e){}

    }

    @FXML
    void btnForwardAction(ActionEvent event) {
        try {
            if (pathHistoryI < pathHistory.size()){
                pathHistoryI+=1;
                updateList(pathHistory.get(pathHistoryI));
                isBack=true;
            }
        }catch (RuntimeException e){}

    }

    @FXML
    void btnPathUpAction(ActionEvent event) {
        Path upperPath= Paths.get(pathField.getText()).getParent();
        if (upperPath!=null){
            updateList(upperPath);
        }

    }
    @FXML
    public void btnRefreshAction(ActionEvent actionEvent) {
        updateList(Path.of(pathField.getText()));
        updateTreeView();
    }
    @FXML
    void selectDiskAction(ActionEvent event) {
        ComboBox<String> element=(ComboBox<String>)event.getSource();
        updateList(Paths.get(element.getSelectionModel().getSelectedItem()));
        pathHistory.clear();
        pathHistory.add(Paths.get(pathField.getText()));
    }
    @FXML
    void initialize() {
        fileTypeInfo.setCellValueFactory(param-> new SimpleStringProperty(param.getValue().getType().getName()));
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
        filesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        disksBox.getItems().clear();
        for (Path p: FileSystems.getDefault().getRootDirectories()){
            disksBox.getItems().add(p.toString());
        }
        //disksBox.getSelectionModel().select(-1);
        ContextMenu contextMenu=factory.createMenu();
        contextMenu.setOnHidden(windowEvent -> {
            updateList(Path.of(pathField.getText()));
            updateTreeView();
        });
        filesTable.setContextMenu(contextMenu);

        filesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount()==2){//открытие файлов/директории
                    Path path=filesTable.getSelectionModel().getSelectedItem().getFilePath();
                    if (Files.isDirectory(path)){
                        updateList(path);
                        for (int i = 0; i < pathHistory.size(); i++) {
                            System.out.println(pathHistory.get(i));
                        }
                        if (isBack){
                            System.out.println(isBack);
                            for (int i = pathHistoryI; i <=pathHistory.size() ; i++) {
                                pathHistory.remove(i);
                            }
                            pathHistory.add(Paths.get(pathField.getText()));
                            pathHistoryI+=1;
                            isBack=false;
                        }else {
                            pathHistory.add(Paths.get(pathField.getText()));
                            pathHistoryI+=1;
                        }

                        System.out.println(pathHistoryI);
                    }else {
                        try {
                            Process command=Runtime.getRuntime().exec("xdg-open "+path);
                            //dt.open(path.toFile());
                        }
                        catch (IOException e) {

                            Alert alert=new Alert(Alert.AlertType.ERROR,"Не удалось открыть файл",ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                }
                /*if(searchStatus){
                    if (mouseEvent.getClickCount()==2){//открытие файлов/директории
                        Path path=filesTable.getSelectionModel().getSelectedItem().getFilePath();
                        if (Files.isDirectory(path)){
                            updateList(path);
                            for (int i = 0; i < pathHistory.size(); i++) {
                                System.out.println(pathHistory.get(i));
                            }
                            if (isBack){
                                System.out.println(isBack);
                                for (int i = pathHistoryI; i <=pathHistory.size() ; i++) {
                                    pathHistory.remove(i);
                                }
                                pathHistory.add(Paths.get(pathField.getText()));
                                pathHistoryI+=1;
                                isBack=false;
                            }else {
                                pathHistory.add(Paths.get(pathField.getText()));
                                pathHistoryI+=1;
                            }

                            System.out.println(pathHistoryI);
                        }else {
                            try {
                                Process command=Runtime.getRuntime().exec("xdg-open "+path);
                                //dt.open(path.toFile());
                            }
                            catch (IOException e) {

                                Alert alert=new Alert(Alert.AlertType.ERROR,"Не удалось открыть файл",ButtonType.OK);
                                alert.showAndWait();
                            }
                        }
                    }

                }else {
                    if (mouseEvent.getClickCount()==2){//открытие файлов/директории
                        Path path=Paths.get(pathField.getText()).resolve(filesTable.getSelectionModel().getSelectedItem().getFileName());
                        if (Files.isDirectory(path)){
                            updateList(path);
                            for (int i = 0; i < pathHistory.size(); i++) {
                                System.out.println(pathHistory.get(i));
                            }
                            if (isBack){
                                System.out.println(isBack);
                                for (int i = pathHistoryI; i <=pathHistory.size() ; i++) {
                                    pathHistory.remove(i);
                                }
                                pathHistory.add(Paths.get(pathField.getText()));
                                pathHistoryI+=1;
                                isBack=false;
                            }else {
                                pathHistory.add(Paths.get(pathField.getText()));
                                pathHistoryI+=1;
                            }

                            System.out.println(pathHistoryI);
                        }else {
                            try {
                                Process command=Runtime.getRuntime().exec("xdg-open "+path);
                                //dt.open(path.toFile());
                            }
                            catch (IOException e) {

                                Alert alert=new Alert(Alert.AlertType.ERROR,"Не удалось открыть файл",ButtonType.OK);
                                alert.showAndWait();
                            }
                        }
                    }
                }*/

                if (mouseEvent.getButton()==MouseButton.SECONDARY){//показ контекс меню
                    contextMenu.show(filesTable,mouseEvent.getScreenX(),mouseEvent.getScreenY());
                    if (Objects.isNull(filesTable.getSelectionModel().getSelectedItem())){
                        List<String> selectedPaths = new ArrayList<>();
                        selectedPaths.add(pathField.getText());
                        factory.setSelectedPaths(selectedPaths);
                        //factory.setFile(new File(pathField.getText()));
                        factory.setEmptyChoose(true);
                    }else {
                        List<String> selectedPaths = new ArrayList<>();

                        // Получите список выделенных объектов из TreeView
                        for (FileInfo selectedItem : filesTable.getSelectionModel().getSelectedItems()) {

                            String path = getPath(selectedItem);
                            if (path != null) {
                                selectedPaths.add(path);
                            }
                        }
                        Path path=Paths.get(pathField.getText()).resolve(filesTable.getSelectionModel().getSelectedItem().getFileName());
                        //factory.setFile(path.toFile());
                        factory.setSelectedPaths(selectedPaths);
                        factory.setEmptyChoose(false);
                    }
                }

            }

        });

        treeViewPaths.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount()==2){
                    TreeItem<FileInfo> selectedItem = treeViewPaths.getSelectionModel().getSelectedItem();
                    Path selectedFolderPath = selectedItem.getValue().getFilePath();
                    updateList(selectedFolderPath);

                    /*Path path=Paths.get(String.valueOf(treeViewPaths.getSelectionModel().getSelectedItem().getValue()));
                    updateList(path);
                    System.out.println(path);*/
                }
            }
        });




        //updateList(Paths.get("."));
        updateList(root);
        updateTreeView();


        /*filesTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                TableRow row=new TableRow<>();
                contextMenu.show(filesTable,event.getScreenX(),event.getScreenY());
                File selectedFile;
                if(Objects.isNull(row.getItem())){
                    factory.setFile(new File(pathField.getText()));
                }else {
                    selectedFile= (File) row.getItem();
                    factory.setFile(selectedFile);
                }
            }
        });*/


        /*filesTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton()==MouseButton.SECONDARY){
                contextMenu.show(filesTable,mouseEvent.getScreenX(),mouseEvent.getScreenY());
                if (Objects.isNull(filesTable.getSelectionModel().getSelectedItem())){
                    factory.setFile(new File(pathField.getText()));
                }else {
                    Path path=Paths.get(pathField.getText()).resolve(filesTable.getSelectionModel().getSelectedItem().getFileName());
                    factory.setFile(path.toFile());
                }
            }
        });*/
        /*filesTable.setRowFactory(tv->{
            TableRow row=new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton()== MouseButton.SECONDARY){
                    contextMenu.show(filesTable,event.getScreenX(),event.getScreenY());
                    File selectedFile;
                    if(Objects.isNull(row.getItem())){
                        factory.setFile(new File(pathField.getText()));
                    }else {
                        selectedFile= (File) row.getItem();
                        factory.setFile(selectedFile);
                    }
                }
            });
            return row;
        });*/

    }
    /*public void init(Node nodeEvent){
        ContextMenu contextMenu=factory.createMenu(nodeEvent);
        contextMenu.setOnHidden(windowEvent -> {
            updateList(Path.of(pathField.getText()));
            updateTreeView();
        });
    }*/


    private String getPath(FileInfo item) {
        // Получите полный путь к объекту из TreeView
        StringBuilder pathBuilder = new StringBuilder(pathField.getText()+"/"+ item.getFileName());

        return pathBuilder.toString();
    }
    /*public void addNode(TreeItem parentNode, File file){
        //FileInfo fileInfo=new FileInfo(file);
        if(file.isDirectory()){
            TreeItem node=new TreeItem(file);
            node.setExpanded(true);
            parentNode.getChildren().add(node);
            for (File subFile : Objects.requireNonNull(file.listFiles())) {
                addNode(node, subFile);
            }
        }
        *//*TreeItem node=new TreeItem(file);
        node.setExpanded(true);
        parentNode.getChildren().add(node);
        if(file.isDirectory()){
            for (File subFile : Objects.requireNonNull(file.listFiles())) {
                addNode(node, subFile);
            }
        }*//*
    }
    public void updateTreeView(){
        TreeItem treeRoot= new TreeItem(new FileInfo(root).getFileName());
        treeViewPaths.setRoot(treeRoot);
        addNode(treeViewPaths.getRoot(), root.toFile());
    }*/

    public void addNode(TreeItem<FileInfo> parentItem, Path folderPath){
        try {

            Files.list(folderPath)
                    .map(FileInfo::new)
                    .forEach(fileInfo -> {
                        if (fileInfo.getType() == FileInfo.FileType.DIRECTORY){
                            TreeItem<FileInfo> item = new TreeItem<>(fileInfo);
                            parentItem.getChildren().add(item);
                            addNode(item, fileInfo.getFilePath());

                        }

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        /*FileInfo parentFileInfo = parentItem.getValue();
        if (parentFileInfo.getType() == FileInfo.FileType.DIRECTORY) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(parentFileInfo.getFilePath())) {
                for (Path path : directoryStream) {
                    FileInfo fileInfo = new FileInfo(path);
                    TreeItem<FileInfo> childItem = new TreeItem<>(fileInfo);
                    parentItem.getChildren().add(childItem);
                    addNode(childItem); // Рекурсивно строим дочерние элементы
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public void updateTreeView(){
        TreeItem<FileInfo> rootItem = new TreeItem<>(new FileInfo(root));
        treeViewPaths.setRoot(rootItem);

// Загружаем дочерние элементы папки
        addNode(rootItem, root);

    }

    public void searchUpdateList(List<FileInfo> findList){
        filesTable.getItems().clear();
        filesTable.getItems().addAll(findList);
        filesTable.sort();
    }

    public void updateList(Path path){
        try {
            //Path pathCurrent = Paths.get(pathField.getText());
            //pathHistory.add(pathCurrent);
            pathField.setText(path.normalize().toAbsolutePath().toString());
            filesTable.getItems().clear();
            filesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
            filesTable.sort();

        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.WARNING,"Данный путь некорректный", ButtonType.OK);
            alert.showAndWait();
        }
    }



}
