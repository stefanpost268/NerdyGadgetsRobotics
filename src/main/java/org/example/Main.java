package org.example;

import com.fazecast.jSerialComm.SerialPort;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SerialPort sp = SerialPort.getCommPort("tty.usbmodem142401");

        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        if(!sp.openPort()) {
            System.out.println("Port not opened");
            return;
        }

        Scanner data = new Scanner(sp.getInputStream());
        while (data.hasNextLine()) {
            System.out.println(data.nextLine());
        }
        sp.closePort();
        data.close();
    }
}