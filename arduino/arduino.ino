#define SLAVE_ADDRESS 9 // Address of this Arduino

#include <Wire.h>
#include "Src/Modules/LightSensorModule/LightSensor.h"
#include "Vendor/ArduinoJson-v7.0.4.h"
#include "Src/Modules/JsonRobotModule/JsonRobot.h"
#include "Src/Enum/RobotStateEnum.h"
#include "Src/Enum/LabelEnum.h"
#include "Src/Modules/EmergencyButtonModule/EmergencyButton.h"
#include "Src/Modules/InductiveSensorModule/InductiveSensor.h"

// lightSensor
LightSensor lightSensor = LightSensor(2);
EmergencyButton emergencyButton = EmergencyButton(5, 10);
JsonRobot jsonrobot = JsonRobot();
InductiveSensor inductiveSensor1 = InductiveSensor(4);
InductiveSensor inductiveSensor2 = InductiveSensor(7);
InductiveSensor inductiveSensor3 = InductiveSensor(6);
InductiveSensor klikSensor = InductiveSensor(1);

const int sensorPin = A0;
bool SAFETY_MODE = false;

int adirectionPin = 12;
int apwmPin = 3;
int abrakePin = 9;

int bdirectionPin = 13;
int bpwmPin = 11;
int bbrakePin = 8;

float speedy = 0.50;
float speedx = 0.75;

int xas = A3;
int yas = A2;

bool command;

void setup()
{
    Serial.begin(9600);

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
}

void loop()
{
    //LightSensor 
    if (!lightSensor.isActive() && !SAFETY_MODE) { 
        jsonrobot.emitRobotState("STATE", "EMERGENCY_STOP", "warehouse is tilted");
        SAFETY_MODE = true;
    }

    //EmergencyStop
    if (emergencyButton.isEmergencyStopPressed() && !SAFETY_MODE && !emergencyButton.isResetPressed()) { 
        jsonrobot.emitRobotState("STATE", "EMERGENCY_STOP", "Emergency button was pressed");
        SAFETY_MODE = true;
    }

    //reset
    if(SAFETY_MODE) {
        if (!emergencyButton.isEmergencyStopPressed() && emergencyButton.isResetPressed()) {
        jsonrobot.emitRobotState("STATE", "MANUAL_MODE", "Reset button was pressed");
            SAFETY_MODE = false;
        }

        return;
    }

    int x = analogRead(xas);
    int y = analogRead(yas);
    x = map(x, 0, 1023, -254, 255);
    y = map(y, 0, 1023, -254, 255);

    if (!lightSensor.isActive()) {
        lightSensor.emitWarehouseTiltedStatus();
        SAFETY_MODE = true;
    }

    // controls for x axes
    if (x > 50 && command ==1 && SAFETY_MODE == false && inductiveSensor1.readInductiveSensor() != 0) {
        digitalWrite(bbrakePin, LOW);
        left(x);
    }
    else if (x < -50 && command ==1 && SAFETY_MODE == false && inductiveSensor2.readInductiveSensor() != 0) {
        digitalWrite(bbrakePin, LOW);
        right(x);
    }
    else {
        digitalWrite(bbrakePin, HIGH);    
    }

    // controls for y axes
    if (y > 50 && command ==1 && SAFETY_MODE == false && klikSensor.readInductiveSensor() != 0){
        digitalWrite(abrakePin, LOW);
        up(y);
    }
    else if(y < -50 && command ==1 && SAFETY_MODE == false && inductiveSensor3.readInductiveSensor() != 0){
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
    SAFETY_MODE = true;
  }
}

