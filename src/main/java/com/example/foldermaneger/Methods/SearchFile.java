package com.example.foldermaneger.Methods;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Path;

public class SearchFile {
     /*filesTable.getItems().clear();
     filesTable.getItems().addAll(Files.list(path).map(FileInfo::new).collect(Collectors.toList()));
     filesTable.sort();

    public static List<FileInfo> searchFiles(Path directory, String query) {
        List<?> searchList;
        try {
            searchList = (List<?>) Files.list(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File dir = new File(directory);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Рекурсивный вызов метода поиска для вложенных папок
                    searchList.addAll(searchFiles(file.getAbsolutePath(), query));
                } else if (file.getName().contains(query)) {
                    // Файл соответствует поисковому запросу
                    searchList.add(file.getAbsolutePath());
                }
            }
        }

        return searchList;
    }*/
    public  List<FileInfo> searchFiles(Path directory, String query) {
        List<FileInfo> searchResults = new ArrayList<>();
        List<Path> testpath = new ArrayList<>();

        try {
            Files.walk(directory)
                    .filter(path -> path.getFileName().toString().contains(query))
                    //.forEach(path -> testpath.add(path));
                    .forEach(path -> searchResults.add(new FileInfo(path)));
            //System.out.println(testpath.get(0));
        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибки при доступе к файлам
        }

        return searchResults;
    }
}


