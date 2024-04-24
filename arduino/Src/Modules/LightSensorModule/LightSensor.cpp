#include <Arduino.h>
#include "LightSensor.h"

LightSensor::LightSensor(int pin) {
    this->pin = pin;
    pinMode(pin, INPUT);
}

bool LightSensor::isActive() {
    return analogRead(pin) > 100;
}