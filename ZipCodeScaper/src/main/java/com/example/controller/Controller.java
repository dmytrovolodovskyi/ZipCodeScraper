package com.example.controller;

import com.example.service.DBService;
import com.example.service.DataService;
import com.example.service.FileService;

public class Controller {
    private static final String FILE_NAME = "allCountries";
    private static final String URL = "https://download.geonames.org/export/zip/" + FILE_NAME + ".zip";
    private static final String SAVE_DIR = "src\\main\\resources\\files\\";
    private static final String EXTRACTED_FILE_NAME = FILE_NAME + ".txt";

    private final FileService file;
    private final DataService data;
    private final DBService db;

    public Controller() {
        file = new FileService();
        data = new DataService();
        db = new DBService();
    }

    public void start(){
        downloadFile();
        readData();
        convertData();
        pushData();
    }

    private void downloadFile() {
        file.ensureDirectoryExists(SAVE_DIR);
        file.downloadFile(URL, SAVE_DIR + FILE_NAME + ".zip");
    }

    private void readData() {
        file.readFile(SAVE_DIR + FILE_NAME + ".zip", EXTRACTED_FILE_NAME);
    }

    private void convertData() {
        data.convertData(file.getData());
    }

    private void pushData() {
        db.pushData(data.getCountryEntities());
        db.pushData(data.getAddressEntities());
    }
}
