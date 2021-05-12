package ru.Katyanka8bit;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FilePaths {
    public static List<File> getListPaths(String directory) {
        if (directory != null) {
            try {
                return Files.walk(Paths.get(directory))
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                System.out.println("Вы ввели неправильный путь, попробуйте снова");
                return null;
            }
        }
        return null;
    }
}