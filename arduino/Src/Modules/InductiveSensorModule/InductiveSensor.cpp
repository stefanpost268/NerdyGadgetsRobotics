#include <Arduino.h>
#include "InductiveSensor.h"

InductiveSensor::InductiveSensor(int InductiveSensorPin) {
    this->InductiveSensorPin = InductiveSensorPin;
    pinMode(InductiveSensorPin, INPUT_PULLUP);
}

int InductiveSensor::readInductiveSensor() {
    return digitalRead(InductiveSensorPin);
}