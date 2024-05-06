#include <Arduino.h>
#include "cMotorController.h"

cMotorController::cMotorController(int cdirectionPin, int cpwmPin, int cbrakePin, int cencoder1, int cencoder2) {
    this->cdirectionPin = cdirectionPin;
    this->cpwmPin = cpwmPin;
    this->cbrakePin = cbrakePin;
    this->cencoder1 = cencoder1;
    this->cencoder2 = cencoder2;
    setup();
}

void cMotorController::setup() {
    pinMode(cdirectionPin, OUTPUT);
    pinMode(cpwmPin, OUTPUT);
    pinMode(cbrakePin, OUTPUT);
    pinMode(cencoder1, INPUT);
    pinMode(cencoder2, INPUT);
}

void cMotorController::vorkForwards(int y) {
    digitalWrite(cdirectionPin, LOW);
    analogWrite(cpwmPin, abs(y));
}

void cMotorController::vorkBackwards(int y) {
    digitalWrite(cdirectionPin, HIGH);
    analogWrite(cpwmPin, y);
}

void cMotorController::driveVork(int y, int IR1) {
    if (y < -50 && IR1 > 140)
    {
        disableBrake();
        vorkForwards(y);
    }
    else if (y > 50  && IR1 < 390)
    {
        disableBrake();
        vorkBackwards(y);
    }
    else {
        enableBrake();
    }
}

void cMotorController::enableBrake() {
    digitalWrite(cbrakePin, HIGH);
}

void cMotorController::disableBrake() {
    digitalWrite(cbrakePin, LOW);
}
