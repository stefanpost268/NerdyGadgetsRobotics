#include <Arduino.h>
#include "MotorController.h"

MotorController::MotorController(int directionPin, int pwmPin, int brakePin, int encoder1) {
    this->directionPin = directionPin;
    this->pwmPin = pwmPin;
    this->brakePin = brakePin;
    this->encoder1 = encoder1;
    setup();
}

void MotorController::setup() {
    pinMode(directionPin, OUTPUT);
    pinMode(pwmPin, OUTPUT);
    pinMode(brakePin, OUTPUT);
    pinMode(encoder1, INPUT);
}

void MotorController::vorkForwards(int y) {
    digitalWrite(directionPin, LOW);
    analogWrite(pwmPin, abs(y));
}

void MotorController::vorkBackwards(int y) {
    digitalWrite(directionPin, HIGH);
    analogWrite(pwmPin, y);
}

void MotorController::driveVork(int y, int IRSensorWaarde) {
    if (y < -50 && IRSensorWaarde > 140)
    {
        disableBrake();
        vorkForwards(y);
    }
    else if (y > 50  && IRSensorWaarde < 390)
    {
        disableBrake();
        vorkBackwards(y);
    }
    else {
        enableBrake();
    }
}

void MotorController::enableBrake() {
    digitalWrite(brakePin, HIGH);
}

void MotorController::disableBrake() {
    digitalWrite(brakePin, LOW);
}
