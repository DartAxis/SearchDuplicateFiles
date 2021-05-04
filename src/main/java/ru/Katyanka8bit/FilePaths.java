package ru.Katyanka8bit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FilePaths {
    public static List<File> getPaths(String directory) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите путь директории");
            try {
                directory = sc.nextLine();
                if (directory.isEmpty()) {
                    System.out.println("Путь не может быть нулевым");
                    continue;
                }
                try {
                    return Files.walk(Paths.get(directory))
                            .filter(Files::isRegularFile)
                            .map(Path::toFile)
                            .collect(Collectors.toList());
                } catch (Exception e ) {
                    System.out.println("Вы ввели неправильный путь, попробуйте снова");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
