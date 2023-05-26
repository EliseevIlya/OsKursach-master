package com.example.foldermaneger.Methods;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class AutoDetect implements Runnable {
    private ComboBox<String> disksBox;
    public AutoDetect(ComboBox<String> disksBox) {
        this.disksBox = disksBox;
    }


    static File[] oldListRoot = File.listRoots();
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (File.listRoots().length > oldListRoot.length) {
                System.out.println("new drive detected");
                oldListRoot = File.listRoots();
                System.out.println("drive" + oldListRoot[oldListRoot.length - 1] + " detected");
                Platform.runLater(() -> {
                    // Тут выводим результат в интерфейс
                    disksBox.getItems().clear();

                    for (Path p: FileSystems.getDefault().getRootDirectories()){
                        disksBox.getItems().add(p.toString());
                    }
                });

            } else if (File.listRoots().length < oldListRoot.length) {
                System.out.println(oldListRoot[oldListRoot.length - 1] + " drive removed");
                oldListRoot = File.listRoots();
                Platform.runLater(() -> {
                    // Тут выводим результат в интерфейс
                    disksBox.getItems().clear();
                    for (Path p: FileSystems.getDefault().getRootDirectories()){
                        disksBox.getItems().add(p.toString());
                    }
                });
            }

        }
    }
}
