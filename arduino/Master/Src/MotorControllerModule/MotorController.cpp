#include <Arduino.h>
#include "MotorController.h"

MotorController::MotorController(int directionPin, int pwmPin, int brakePin) {
    this->directionPin = directionPin;
    this->pwmPin = pwmPin;
    this->brakePin = brakePin;
    setup();
}

void MotorController::setup() {
    pinMode(directionPin, OUTPUT);
    pinMode(pwmPin, OUTPUT);
    pinMode(brakePin, OUTPUT);
}

void MotorController::vorkForwards(int y) {
    digitalWrite(directionPin, LOW);
    analogWrite(pwmPin, abs(y));
}

void MotorController::vorkBackwards(int y) {
    digitalWrite(directionPin, HIGH);
    analogWrite(pwmPin, y);
}

void MotorController::driveVork(int y, int IRSensorWaarde, bool EmergencyButtonState) {
    if (!EmergencyButtonState) {
        if (y < -50 && IRSensorWaarde > 160)
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
