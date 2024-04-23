package org.example;

import com.fazecast.jSerialComm.SerialPort;



public class Main {
    public static void main(String[] args) {
        SerialPort sp = SerialPort.getCommPort("");
        System.out.println(sp);
    }
}