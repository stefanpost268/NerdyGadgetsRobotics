#define SLAVE_ADDRESS 9 // Address of this Arduino

#include <Wire.h>
#include "Src/Modules/LightSensorModule/LightSensor.h"
#include "Vendor/ArduinoJson-v7.0.4.h"
#include "Src/Modules/JsonRobotModule/JsonRobot.h"
#include "Src/Enum/RobotStateEnum.h"
#include "Src/Enum/LabelEnum.h"
#include "Src/Modules/EmergencyButtonModule/EmergencyButton.h"
#include "Src/Modules/InductiveSensorModule/InductiveSensor.h"
#include "Src/Modules/MotorControllerModule/MotorController.h"

// lightSensor
LightSensor lightSensor = LightSensor(2);
EmergencyButton emergencyButton = EmergencyButton(5, 10);
JsonRobot jsonrobot = JsonRobot();
InductiveSensor inductiveSensor1 = InductiveSensor(4);
InductiveSensor inductiveSensor2 = InductiveSensor(7);
InductiveSensor inductiveSensor3 = InductiveSensor(6);
InductiveSensor klikSensor = InductiveSensor(1);
MotorController motorcontrollerxas = MotorController(12, 3, 9, A1, 1);
MotorController motorcontrolleryas = MotorController(13, 11, 8, A0, 1);

bool SAFETY_MODE = false;

int xas = A3;
int yas = A2;

bool vorkOpen;

void setup()
{
    Serial.begin(9600);

    pinMode(yas, INPUT);
    pinMode(xas, INPUT);

    Wire.begin(SLAVE_ADDRESS); // Initialize I2C communication with address
    Wire.onReceive(receiveEvent); // Set up a function to handle received data
    Wire.onRequest(requestEvent); // Set up a function to handle requests for data
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
    x = map(x, 0, 1023, 255, -255);
    y = map(y, 0, 1023, -255, 255);

    if (!lightSensor.isActive()) {
        lightSensor.emitWarehouseTiltedStatus();
        SAFETY_MODE = true;
    }

    // controls for x axes
    motorcontrollerxas.driveMotor(x, inductiveSensor1.readInductiveSensor(), inductiveSensor2.readInductiveSensor(), false, vorkOpen);

    // controls for y axes
    motorcontrolleryas.driveMotor(y, inductiveSensor3.readInductiveSensor(), 1, false, 0);
}

void receiveEvent(bool numBytes) {
  if (Wire.available() > 0) {
    vorkOpen = Wire.read(); // Read the received command

    Serial.print("Received command: ");
    Serial.println(vorkOpen);
  }else{
    SAFETY_MODE = true;
  }
}

void requestEvent() {
  Wire.write(lightSensor.isActive());
}

