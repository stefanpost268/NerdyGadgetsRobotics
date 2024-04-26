#include "IRSensor.h"

// Implement the functions defined in the header file

// Constructor
IRSensor::IRSensor() {
    // motor pins
    pinMode(cdirectionPin, OUTPUT);
    pinMode(cpwmPin, OUTPUT);
    pinMode(cbrakePin, OUTPUT);
    pinMode(cencoder1, INPUT);
    pinMode(cencoder2, INPUT);
}

// Functie die de vork vooruit laat bewegen
void IRSensor::vorkForwards(int y) {
    digitalWrite(cdirectionPin, LOW);
    analogWrite(cpwmPin, abs(y));
}

// Functie die de vork achteruit laat bewegen
void IRSensor::vorkBackwards(int y) {
    digitalWrite(cdirectionPin, HIGH);
    analogWrite(cpwmPin, y);
}

// Functie die de vork aanstuurt
void driveVork(int y) {
    if (y < -50 && IR1 > 140)
    {
        digitalWrite(cbrakePin, LOW);
        vorkForwards(y);
    }
    else if (y > 50  && IR1 < 390)
    {
        digitalWrite(cbrakePin, LOW);
        vorkBackwards(y);
    }
    else {
        digitalWrite(cbrakePin, HIGH);
    }
}
