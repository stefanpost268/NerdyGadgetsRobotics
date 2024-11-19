#include <Arduino.h>
#include "Communication.h"
#include <Wire.h>


Communication::Communication(int workerAdress) {
    Wire.begin();
    this->workerAdress = workerAdress;
}


void Communication::sendInformationToMaster(String label , String data) {
    Wire.beginTransmission(workerAdress);
    Wire.write((label+":"+data).c_str()); 
    Wire.endTransmission();
}
