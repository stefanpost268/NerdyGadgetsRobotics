#include <Arduino.h>
#include "MotorController.h"

MotorController::MotorController(int directionPin, int pwmPin, int brakePin, int encoder1, int encoder2, float speedmultiplier) {
    this->directionPin = directionPin;
    this->pwmPin = pwmPin;
    this->brakePin = brakePin;
    this->encoder1 = encoder1;
    this->encoder2 = encoder2;
    this->speedmultiplier = speedmultiplier;
    setup();
}

void MotorController::setup() {
    pinMode(directionPin, OUTPUT);
    pinMode(pwmPin, OUTPUT);
    pinMode(brakePin, OUTPUT);
    pinMode(encoder1, INPUT);
    pinMode(encoder2, INPUT);
}

void MotorController::motorForwards(int joystickInput) {
    digitalWrite(directionPin, LOW);
    analogWrite(pwmPin, abs(joystickInput));
}

void MotorController::motorBackwards(int joystickInput) {
    digitalWrite(directionPin, HIGH);
    analogWrite(pwmPin, (joystickInput));
}

void MotorController::readEncoder() {
    bool encoder1 = digitalRead(this->encoder1);
    bool encoder2 = digitalRead(this->encoder2);

    if (encoder1 == encoder2) {
        motorLocation++;
    }
    else if (motorLocation > 0)
     {
        motorLocation--;
    }
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

int MotorController::getMotorLocation() {
    return motorLocation;
}

int MotorController::getMotorLocationAsCoordinate(int max, int columnCount) {
        
        int columnSize = max / columnCount;

        for (size_t i = 0; i <= columnCount; i++)
        {
            if (motorLocation > columnSize * i && motorLocation < columnSize * (i + 1))
            {
                return i + 1;
            }
            
        }
        return 0; 
    }