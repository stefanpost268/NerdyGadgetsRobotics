#include "Src/Modules/LightSensorModule/LightSensor.h"
#include "Vendor/ArduinoJson-v7.0.4.h"
#include "Src/Modules/JsonRobotModule/JsonRobot.h"
#include "Src/Enum/RobotStateEnum.h"
#include "Src/Enum/LabelEnum.h"
#include "Src/Modules/EmergencyButtonModule/EmergencyButton.h"

// lightSensor
LightSensor lightSensor = LightSensor(A0);
EmergencyButton emergencyButton = EmergencyButton(8, 10);
JsonRobot jsonrobot = JsonRobot();

const int sensorPin = A0;
bool SAFETY_MODE = false;


void setup() {
  Serial.begin(9600);
}

void loop() {

  // //LightSensor 
  // if (!lightSensor.isActive() && !SAFETY_MODE) { 
  //    jsonrobot.emitRobotState(STATE, EMERGENCY_STOP, "warehouse is tilted");
  // }

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

}


