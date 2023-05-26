package com.example.foldermaneger.Methods;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInfo {
    public enum FileType{
        FILE("F"),DIRECTORY("D");
        private String name;
        FileType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    private String fileName;
    private FileType type;
    private long size;
    private LocalDateTime lastModefied;
    private Path filePath;

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    private TreeItem<FileInfo> treeItem;

    public TreeItem<FileInfo> getTreeItem() {
        return treeItem;
    }

    public void setTreeItem(TreeItem<FileInfo> treeItem) {
        this.treeItem = treeItem;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getLastModefied() {
        return lastModefied;
    }

    public void setLastModefied(LocalDateTime lastModefied) {
        this.lastModefied = lastModefied;
    }

    @Override
    public String toString() {
        return fileName;
    }

    public FileInfo (Path path){
        try {
            this.fileName=path.getFileName().toString();
            this.size= Files.size(path);
            this.type=Files.isDirectory(path) ? FileType.DIRECTORY : FileType.FILE;
            this.filePath=path;
            this.treeItem = new TreeItem<>(this);
            if (this.type==FileType.DIRECTORY){
                this.size=-1L;
            }
            this.lastModefied=LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.ofHours(0));
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR,"Невозможно получить информацию о файле", ButtonType.OK);
            alert.showAndWait();
            //throw new RuntimeException("Невозможно получить информацию о файле");
        }
    }
}
