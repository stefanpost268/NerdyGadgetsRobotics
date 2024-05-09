#include <Arduino.h>
#include "IRSensor.h"

IRSensor::IRSensor(int IRSensorPin) {
    this->IRSensorPin = IRSensorPin;
    setup();
}

void IRSensor::setup() {
    pinMode(IRSensorPin, INPUT);
}

int IRSensor::readIRSensor() {
    return analogRead(IRSensorPin);
}
