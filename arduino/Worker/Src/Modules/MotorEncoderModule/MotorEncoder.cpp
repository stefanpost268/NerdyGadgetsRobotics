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
    if (digitalRead(this->encoder2Pin)) {
        motorLocation++;
    }
    else {
        motorLocation--;
    }
}

volatile int MotorEncoder::getMotorLocation() {
    return motorLocation;
}

void MotorEncoder::resetEncoder() {
    motorLocation = 0;
}

void MotorEncoder::calibrate() {
    resetEncoder();
    
}

int MotorEncoder::getMotorLocationAsCoordinate(int max, int columnCount) {
        
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

