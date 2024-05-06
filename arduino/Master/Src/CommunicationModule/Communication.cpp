#include <Arduino.h>
#include "Communication.h"
#include <Wire.h>
#include "../IRSensorModule/IRSensor.h"

Communication::Communication(int workerAdress) {
    Wire.begin();
    this->workerAdress = workerAdress;
}

bool Communication::readVorkState(IRSensor sensor) {
    if (sensor.readIRSensor() > 380) {
        vorkOpen = true;
    } else {
        vorkOpen = false;
    }
}

void Communication::sendVorkStateToWorker(IRSensor sensor) {
    Wire.beginTransmission(workerAdress);
    Wire.write(readVorkState(sensor));                            
    Wire.endTransmission();                   

    /**
     * For debugging purposes
     * Remove this when done!
    */
    Serial.print("Vork open: ");
    Serial.println(readVorkState(sensor));
}
