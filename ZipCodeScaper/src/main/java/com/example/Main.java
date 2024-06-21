package com.example;

import com.example.controller.Controller;

public class Main {
    /*
    Empty database before starting.
    Otherwise, the data will be duplicated
     */

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }
}