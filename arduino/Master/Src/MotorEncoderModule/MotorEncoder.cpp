#include "Arduino.h"
#include "MotorEncoder.h"

MotorEncoder::MotorEncoder(int encoder1Pin, int encoder2Pin) {
    this->encoder1Pin = encoder1Pin;
    this->encoder2Pin = encoder2Pin;
    setup();
}

void MotorEncoder::setup() {
    pinMode(encoder1Pin, INPUT_PULLUP);
    pinMode(encoder2Pin, INPUT_PULLUP);
}

void MotorEncoder::readEncoder() {
    if (digitalRead(this->encoder1Pin) == digitalRead(this->encoder2Pin)) {
        motorLocation--;
    }
    else {
        motorLocation++;
    }
    return;
}

int MotorEncoder::getMotorLocation() {
    return motorLocation;
}


// Other member function implementations
