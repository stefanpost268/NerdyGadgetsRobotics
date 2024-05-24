#include <Arduino.h>
#include "Communication.h"
#include <Wire.h>
#include "../IRSensorModule/IRSensor.h"
#include "../MotorEncoderModule/MotorEncoder.h"

Communication::Communication(int workerAdress) {
    Wire.begin();
    this->workerAdress = workerAdress;
}

bool Communication::readVorkState(IRSensor sensor) {
    if (sensor.readIRSensor() < 350) {
        return vorkOpen = true;
    } else {
        return vorkOpen = false;
    }
}

void Communication::sendInformationToWorker(String label , String data) {
    Wire.beginTransmission(workerAdress);
    Wire.write((label+":"+data).c_str()); 
    Wire.endTransmission();
}
