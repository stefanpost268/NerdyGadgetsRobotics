#include <Arduino.h>
#include "InductiveSensor.h"

InductiveSensor::InductiveSensor(int InductiveSensorPin) {
    this->InductiveSensorPin = InductiveSensorPin;
    pinMode(InductiveSensorPin, INPUT_PULLUP);
}

bool InductiveSensor::readInductiveSensor() {
    return digitalRead(InductiveSensorPin) == 1;
}