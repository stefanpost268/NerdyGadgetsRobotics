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
#include "Src/Modules/MotorEncoderModule/MotorEncoder.h"

// lightSensor
LightSensor lightSensor = LightSensor(2);
EmergencyButton emergencyButton = EmergencyButton(5, 10);
JsonRobot jsonrobot = JsonRobot();
InductiveSensor inductiveSensorLeft = InductiveSensor(4);
InductiveSensor inductiveSensorRight = InductiveSensor(7);
InductiveSensor inductiveSensorBelow = InductiveSensor(6);
InductiveSensor clickSensorTop = InductiveSensor(1);
MotorController motorcontrollerxas = MotorController(12, 3, 9, 1);
MotorController motorcontrolleryas = MotorController(13, 11, 8, 1);
MotorEncoder motorencoder = MotorEncoder(2, 5);

bool SAFETY_MODE = false;

int xas = A3;
int yas = A2;

int x = 0;
int y = 0;

bool vorkOpen;
int Encoder1;
int Data[2];

void setup()
{
    Serial.begin(9600);

    pinMode(yas, INPUT);
    pinMode(xas, INPUT);

    Wire.begin(SLAVE_ADDRESS); // Initialize I2C communication with address
    Wire.onReceive(receiveEvent); // Set up a function to handle received data
    Wire.onRequest(requestEvent); // Set up a function to handle requests for data

    attachInterrupt(digitalPinToInterrupt(2), []() {
        motorencoder.readEncoder();
    }, RISING);

}

void loop()
{
//



    //LightSensor 
    // if (!lightSensor.isActive() && !SAFETY_MODE) { 
    //     jsonrobot.emitRobotState("STATE", "EMERGENCY_STOP", "warehouse is tilted");
    //     SAFETY_MODE = true;
    //     motorcontrollerxas.emergencyStop();
    //     motorcontrolleryas.emergencyStop();
        
    // }

    //EmergencyStop
    if (emergencyButton.isEmergencyStopPressed() && !SAFETY_MODE && !emergencyButton.isResetPressed()) { 
        // jsonrobot.emitRobotState("STATE", "EMERGENCY_STOP", "Emergency button was pressed");
        // SAFETY_MODE = true;
    }

    //reset
    if(SAFETY_MODE) {
        if (!emergencyButton.isEmergencyStopPressed() && emergencyButton.isResetPressed()) {
        jsonrobot.emitRobotState("STATE", "MANUAL_MODE", "Reset button was pressed");
            SAFETY_MODE = false;
        }
  
        x = 0;
        y = 0;
    }
    else {
      x = map(analogRead(xas), 0, 1023, 255, -255);
      y = map(analogRead(yas), 0, 1023, -255, 255);

      if (!lightSensor.isActive()) {
          // lightSensor.emitWarehouseTiltedStatus();
          // SAFETY_MODE = true;
      }
    }
    // controls for x axes
    motorcontrollerxas.driveMotor(x, inductiveSensorRight.readInductiveSensor(), inductiveSensorLeft.readInductiveSensor(), SAFETY_MODE, vorkOpen);

    // controls for y axes
    motorcontrolleryas.driveMotor(y, inductiveSensorBelow.readInductiveSensor(), clickSensorTop.readInductiveSensor(), SAFETY_MODE, 0);

    Serial.print("Motor location x: ");
    Serial.print(motorencoder.getMotorLocation());
    Serial.print(", ");
    Serial.println(Data[1]);
}

void receiveEvent(int numBytes) {
  if (Wire.available() > 0) {
    Data[0] = Wire.read(); // Read the received command
    Data[1] = Wire.read(); // Read the received command
    vorkOpen = Data[0];
    Encoder1 = Data[1];
  } else {
    SAFETY_MODE = true;
  }
}

void requestEvent() {
  Wire.write(SAFETY_MODE);
}

