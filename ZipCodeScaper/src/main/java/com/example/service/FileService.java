package com.example.service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileService {
    public static final Logger LOGGER = Logger.getLogger(FileService.class.getName());

    private static List<String> dataRows;

    public FileService() {
        dataRows = new ArrayList<>();
    }

    public void ensureDirectoryExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                LOGGER.log(Level.INFO, "Directory created: '" + directoryPath + "'");
            } else {
                LOGGER.log(Level.SEVERE, "Failed to create directory: '" + directoryPath + "'");
            }
        }
    }

    public void downloadFile(String fileURL, String savePath) {
        try {
            URL url = new URL(fileURL);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            FileOutputStream fileOutputStream = new FileOutputStream(savePath);
            byte[] buffer = new byte[1024];
            int bytesRead = -1;

            LOGGER.log(Level.INFO, "Downloading '" + fileURL + "'");

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
            bufferedInputStream.close();
            inputStream.close();
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error downloading file from '" + fileURL + "'", e);
        }

        LOGGER.log(Level.INFO, "Downloading completed! File saved to '" + savePath + "'");
    }

    public void readFile(String zipFilePath, String extractedFileName) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                if (zipEntry.getName().equals(extractedFileName)) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zis));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        dataRows.add(line);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getData() {
        return dataRows;
    }
}
