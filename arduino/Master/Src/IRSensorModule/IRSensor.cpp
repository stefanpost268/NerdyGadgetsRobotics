#include <Arduino.h>
#include "IRSensor.h"

IRSensor::IRSensor(int cIRSensor) {
    this->cIRSensor = cIRSensor;
    setup();
}

void IRSensor::setup() {
    pinMode(cIRSensor, INPUT);
}

int IRSensor::readIRSensor() {
    return analogRead(cIRSensor);
}
