#include <Arduino.h>
#include "MotorController.h"

MotorController::MotorController(int directionPin, int pwmPin, int brakePin, int encoder1, float speedmultiplier) {
    this->directionPin = directionPin;
    this->pwmPin = pwmPin;
    this->brakePin = brakePin;
    this->encoder1 = encoder1;
    this->speedmultiplier = speedmultiplier;
    setup();
}

void MotorController::setup() {
    pinMode(directionPin, OUTPUT);
    pinMode(pwmPin, OUTPUT);
    pinMode(brakePin, OUTPUT);
    pinMode(encoder1, INPUT);
}

void MotorController::motorForwards(int joystickInput) {
    digitalWrite(directionPin, LOW);
    analogWrite(pwmPin, abs(joystickInput));
}

void MotorController::motorBackwards(int joystickInput) {
    digitalWrite(directionPin, HIGH);
    analogWrite(pwmPin, (joystickInput));
}

void MotorController::driveFork(int y, int IRSensorWaarde) {
    if (y < -50 && IRSensorWaarde > 140)
    {
        disableBrake();
        motorForwards(y);
    }
    else if (y > 50  && IRSensorWaarde < 390)
    {
        disableBrake();
        motorBackwards(y);
    }
    else {
        enableBrake();
    }
}

void MotorController::driveMotor(int joystickInput, int sensor1, int sensor2, bool SAFETY_MODE, bool command) {
    if (joystickInput > 50 && command ==1 && SAFETY_MODE == false && sensor1 != 0) {
        disableBrake();
        motorBackwards(joystickInput);
    }
    else if (joystickInput < -50 && command ==1 && SAFETY_MODE == false && sensor2 != 0) {
        disableBrake();
        motorForwards(joystickInput);
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
