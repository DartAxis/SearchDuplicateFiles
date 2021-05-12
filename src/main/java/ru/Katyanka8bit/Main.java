package ru.Katyanka8bit;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        String path = args[0];
        if (args.length == 0) {
            showMenu();
            System.exit(1);
        } else if (args.length > 1) {
            System.out.println("Введите один параметр");
            System.exit(1);
        } else if (args.length == 1) {
            long timeStart = System.currentTimeMillis();
            List<File> listPaths = FilePaths.getListPaths(path);
            Map<String, List<String>> fileDuplicate = new HashMap<>();
            startThread(listPaths, fileDuplicate);


            long timeStop = System.currentTimeMillis();
            System.out.println("Затрачено времени: " + (timeStop - timeStart) / 1000 + " сек.");
            System.out.println("Очистим всего: " + FileSize.getSumFilesSize(fileDuplicate) + "Mb");

        }
    }

    public static void showMenu() {
        System.out.println("Введите параметр");

    }

    public static void startThread(List<File> listPaths, Map<String, List<String>> fileDuplicate) {
        ExecutorService threadExecutor = Executors.newFixedThreadPool(10);
        for (File file : listPaths) {
            threadExecutor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Hello " + threadName);
                String hash = HashSum.getHashSumFile(file.toString());
                if (fileDuplicate.containsKey(hash)) {
                    fileDuplicate.get(hash).add(file.toString());
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(file.toString());
                    fileDuplicate.put(hash, list);
                }
            });
        }
        threadExecutor.shutdown();
        try {
            threadExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
