#include "Src/Modules/LightSensorModule/LightSensor.h"
#include <Wire.h>
#define SLAVE_ADDRESS 9 // Address of this Arduino

LightSensor lightSensor = LightSensor(2);
bool safetyMode = false;

int adirectionPin = 12;
int apwmPin = 3;
int abrakePin = 9;

int bdirectionPin = 13;
int bpwmPin = 11;
int bbrakePin = 8;

float speedy = 0.50;
float speedx = 0.75;

const int xas = A3;
const int yas = A2;

bool command;

void setup()
{
    // define pins
    pinMode(adirectionPin, OUTPUT);
    pinMode(apwmPin, OUTPUT);
    pinMode(abrakePin, OUTPUT);

    pinMode(bdirectionPin, OUTPUT);
    pinMode(bpwmPin, OUTPUT);
    pinMode(bbrakePin, OUTPUT);

    pinMode(A2, INPUT);
    pinMode(A3, INPUT);

    Wire.begin(SLAVE_ADDRESS); // Initialize I2C communication with address
    Wire.onReceive(receiveEvent); // Set up a function to handle received data

    Serial.begin(9600);
}

void loop()
{
    int x = analogRead(xas);
    int y = analogRead(yas);
    x = map(x, 0, 1023, -255, 255);
    y = map(y, 0, 1023, -255, 255);

    if(safetyMode) {
    return;
  }

  if (!lightSensor.isActive()) {
    lightSensor.emitWarehouseTiltedStatus();
    safetyMode = true;
  }

// controls for x axes
    if (x > 50 && command ==1 && safetyMode == false) {
        digitalWrite(bbrakePin, LOW);
        left(x);
    }
    else if (x < -50 && command ==1 && safetyMode == false) {
        digitalWrite(bbrakePin, LOW);
        right(x);
    }
    else {
        digitalWrite(bbrakePin, HIGH);    
    }

    // controls for y axes
    if (y > 50 && command ==1 && safetyMode == false) {
        digitalWrite(abrakePin, LOW);
        up(y);
    }
    else if(y < -50 && command ==1 && safetyMode == false) {
        digitalWrite(abrakePin, LOW);
        down(y);
    }
    else {
        digitalWrite(abrakePin, HIGH);
    }
}

void left(int x) {
    digitalWrite(adirectionPin, LOW);
    analogWrite(apwmPin, x*speedx);
}

void right(int x) {
    digitalWrite(adirectionPin, HIGH);
    analogWrite(apwmPin, abs(x)*speedx);
}

void up(int y) {
    digitalWrite(bdirectionPin, HIGH);
    analogWrite(bpwmPin, y*speedy);
}

void down(int y) {
    digitalWrite(bdirectionPin, LOW);
    analogWrite(bpwmPin, abs(y));
}

void receiveEvent(bool numBytes) {
  if (Wire.available() > 0) {
    bool command = Wire.read(); // Read the received command

    Serial.print("Received command: ");
    Serial.println(command);
  }else{
    safetyMode = true;
  }
}