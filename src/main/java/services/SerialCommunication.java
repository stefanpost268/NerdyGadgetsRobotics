package services;

import com.fazecast.jSerialComm.*;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SerialCommunication {

    private SerialPort serialPort;
    private JSONObject receivedJson = new JSONObject();

    public SerialCommunication() {
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Available ports:");
        int i = 0;
        for (SerialPort port : ports) {
            System.out.println(i + ": " + port.getSystemPortName());
            i++;
        }
        try {
            // Change the index based on the correct port you want to select
            this.serialPort = ports[13];
            System.out.println("Selected port: " + this.serialPort.getSystemPortName());

            serialPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);

            if (serialPort.openPort()) {
                System.out.println("Port is open.");
                setupDataListener();
            } else {
                System.out.println("Failed to open port.");
            }

        } catch (Exception e) {
            System.out.println("Failed to open port.");
        }
    }

    public JSONObject getReceivedJson() {
        return receivedJson;
    }

    private void setupDataListener() {
        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    try
                    {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    byte[] newData = new byte[serialPort.bytesAvailable()];
                    int numRead = serialPort.readBytes(newData, newData.length);
                    String receivedData = new String(newData, 0, numRead, StandardCharsets.UTF_8);
                    try {
                        // Assuming the received data is a JSON string
                        receivedJson = new JSONObject(receivedData);
                        System.out.println("Received JSON: " + receivedJson.toString(2));
                    } catch (Exception e) {
                        System.out.println("Received non-JSON data: " + receivedData);
                    }
                }
            }
        });
    }
}
