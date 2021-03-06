package ru.dartinc;

import ru.dartinc.utils.FilePaths;
import ru.dartinc.utils.FileSize;
import ru.dartinc.utils.HashSum;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String path;
        if (args.length == 0 || args.length > 1) {
            showMenu();
            System.exit(1);
        } else {
            path = args[0];
            long timeStart = System.currentTimeMillis();
            List<File> listPaths = FilePaths.getListPaths(path);
            if (listPaths == null) {
                showMenu();
                LOGGER.log(Level.WARNING,"Неверно указан путь директории или к нему нет доступа");
                System.exit(1);
            }
            Map<String, List<String>> fileDuplicate = new HashMap<>();
            startThread(listPaths, fileDuplicate);


            long timeStop = System.currentTimeMillis();
            long fileSize = FileSize.getSumFilesSize(fileDuplicate);
            String time = "Затрачено времени: " + (timeStop - timeStart) / 1000 + " сек.";
            LOGGER.log(Level.INFO,time);
            if (fileSize == 0) {
                LOGGER.log(Level.INFO,"Дубликаты файлов не найдены");
            }
            String size = "Очистим всего: " + fileSize + "Mb";
            LOGGER.log(Level.INFO,size);

        }
    }

    public static void showMenu() {
        System.out.println("Duplicates v. 1.0");
        System.out.println("java -jar duplicates.jar [path]\n");
        System.out.println("Например: ");
        System.out.println("java -jar duplicates.jar c:\\Windows");

    }

    public static void startThread(List<File> listPaths, Map<String, List<String>> fileDuplicate) {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(10);
        for (File file : listPaths) {
            threadExecutor.submit(() -> {
                String hash = HashSum.getHashSumFile(file.toString());
                if (hash != null) {
                    if (fileDuplicate.containsKey(hash)) {
                        fileDuplicate.get(hash).add(file.toString());
                    } else {
                        List<String> list = new ArrayList<>();
                        list.add(file.toString());
                        fileDuplicate.put(hash, list);
                    }
                }
            });
        }
        threadExecutor.shutdown();
        try {
            threadExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }
}
