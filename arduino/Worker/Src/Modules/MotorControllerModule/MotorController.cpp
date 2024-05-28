#include <Arduino.h>
#include "MotorController.h"

MotorController::MotorController(int directionPin, int pwmPin, int brakePin, float speedmultiplier) {
    this->directionPin = directionPin;
    this->pwmPin = pwmPin;
    this->brakePin = brakePin;
    this->speedmultiplier = speedmultiplier;
    setup();
}

void MotorController::setup() {
    pinMode(directionPin, OUTPUT);
    pinMode(pwmPin, OUTPUT);
    pinMode(brakePin, OUTPUT);
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

void MotorController::driveMotor(int joystickInput, int sensor1, int sensor2, bool SAFETY_MODE, bool vorkOpen) {
    if (!SAFETY_MODE) {
        if (sensor2 == 0)
        {
            // motorLocation = 0;
        }
      
        
        if (joystickInput < -50 && vorkOpen == 0 && sensor2 != 0) 
        {
            disableBrake();
            motorForwards(joystickInput);
        }
        else if (joystickInput > 50 && vorkOpen == 0 && sensor1 != 0) 
        {
            disableBrake();
            motorBackwards(joystickInput);
        }
        
        else 
        {
            enableBrake();   
        }
    }
    else {
        enableBrake();
    }
}

void MotorController::emergencyStop() {
    enableBrake();
}

void MotorController::enableBrake() {
    digitalWrite(brakePin, HIGH);
}

void MotorController::disableBrake() {
    digitalWrite(brakePin, LOW);
}
