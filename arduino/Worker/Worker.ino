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

LightSensor lightSensor = LightSensor(A1);
EmergencyButton emergencyButton = EmergencyButton(10);
JsonRobot jsonrobot = JsonRobot();
InductiveSensor inductiveSensorLeft = InductiveSensor(4);
InductiveSensor inductiveSensorRight = InductiveSensor(7);
InductiveSensor inductiveSensorBelow = InductiveSensor(6);
InductiveSensor clickSensorTop = InductiveSensor(A0);
MotorController motorcontrollerxas = MotorController(12, 3, 9, 1);
MotorController motorcontrolleryas = MotorController(13, 11, 8, 1);
MotorEncoder motorencoder = MotorEncoder(2, 5);

bool SAFETY_MODE = false;
bool Automode = true;
bool calibrated = false;

bool isResetButtonPressed = false;

int xas = A3;
int yas = A2;

int x = 0;
int y = 0;

volatile int yasLocation = 0;

bool vorkOpen;
int Encoder1;
int Data[2];
int locationvisited = 0;

int locationQueue[3][2] = 
{
    {96, 2237},
    {2178, 2237},
    {2178, 713},
}; 

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
    //LightSensor 
    if (!lightSensor.isActive() && !SAFETY_MODE) { 
        jsonrobot.emitRobotState("STATE", "EMERGENCY_STOP", "warehouse is tilted");
        SAFETY_MODE = true;
        motorcontrollerxas.emergencyStop();
        motorcontrolleryas.emergencyStop();
        return;
    }

    if(digitalRead(10) == HIGH && !SAFETY_MODE) {
        jsonrobot.emitRobotState("STATE", "EMERGENCY_STOP", "Emergency button was pressed");
        SAFETY_MODE = true;
        motorcontrollerxas.emergencyStop();
        motorcontrolleryas.emergencyStop();
    }

    //reset
    if(SAFETY_MODE) {
        if (isResetButtonPressed) {
          jsonrobot.emitRobotState("STATE", "MANUAL_MODE", "Reset button was pressed");
          SAFETY_MODE = false;
          isResetButtonPressed = false;

          motorcontrollerxas.disableBrake();
          motorcontrolleryas.disableBrake();
        }
  
        x = 0;
        y = 0;
    }
    else {
      x = map(analogRead(xas), 0, 1023, 255, -255);
      y = map(analogRead(yas), 0, 1023, -255, 255);

      if (!lightSensor.isActive()) {
          lightSensor.emitWarehouseTiltedStatus();
          SAFETY_MODE = true;
      }
    }
    
    if(!SAFETY_MODE){
      if (Automode){
        if (calibrated) {
          goToLocation(motorencoder.getMotorLocation(), yasLocation, locationQueue[0][0], locationQueue[0][1]);
        } else {
          calibrateEncoders();
        }
      } else {
      // controls for x axes
      motorcontrollerxas.driveMotor(x, inductiveSensorRight.readInductiveSensor(), inductiveSensorLeft.readInductiveSensor(), SAFETY_MODE, vorkOpen);

      // controls for y axes
      motorcontrolleryas.driveMotor(y, inductiveSensorBelow.readInductiveSensor(), clickSensorTop.readInductiveSensor(), SAFETY_MODE, 0);
      }

      // Serial.println("Cords: " + (String) motorencoder.getMotorLocation() + ", " + yasLocation + ", cal:" + calibrated + ", kliksns:" + clickSensorTop.readInductiveSensor() + ", cal sensors: " + inductiveSensorLeft.readInductiveSensor() + ", " + inductiveSensorBelow.readInductiveSensor() + ", queue: " + locationQueue[0][0] + ", " + locationQueue[0][1] + ", locations visited: " + locationvisited + ", lightsensor: " + lightSensor.isActive() + ", Safety_mode: " + SAFETY_MODE);
  };
      
}


void receiveEvent(int placeholder) {
  String message = ""; 
  while (Wire.available()) {
    char letter = Wire.read(); 
    message += letter;
  }

  // split string with :
  int index = message.indexOf(":");
  String label = message.substring(0, index);
  String value = message.substring(index + 1);

  if(label == "v") {
    vorkOpen = value.toInt();
  }
  if(label == "y") {
    yasLocation = value.toInt();
  }
  if(label == "r") {
    isResetButtonPressed = true;
  }
}

void calibrateEncoders() {
      if (inductiveSensorLeft.readInductiveSensor() == 0 && inductiveSensorBelow.readInductiveSensor() == 0) {
        motorencoder.resetEncoder();
        yasLocation = 0;
        motorcontrollerxas.motorForwards(0);
        motorcontrolleryas.motorBackwards(0);
        calibrated = true;
      } else {
        motorcontrollerxas.motorForwards(225);
        motorcontrolleryas.motorBackwards(225);
      }
}

void requestEvent() {
  Wire.write(SAFETY_MODE);
}

void goToLocation(int currentxInput, int currentyInput, int targetx, int targety) {
  switch (currentxInput < targetx ? 1 : (currentxInput > targetx ? -1 : 0)) {
    case 1:
      motorcontrollerxas.motorBackwards(185);
      break;
    case -1:
      motorcontrollerxas.motorForwards(185);
      break;
    case 0:
      motorcontrollerxas.enableBrake();
      break;
  }

  switch (currentyInput < targety - 9 ? 1 : (currentyInput > targety + 9 ? -1 : 0)) {
    case 1:
      motorcontrolleryas.motorForwards(175);
      break;
    case -1:
      motorcontrolleryas.motorBackwards(175);
      break;
    case 0:
      motorcontrolleryas.enableBrake();
      break;
  }

  if ((currentxInput > targetx - 10 && currentxInput < targetx + 10) && (currentyInput > targety - 10 && currentyInput < targety + 10)) {
    motorcontrollerxas.enableBrake();
    motorcontrolleryas.enableBrake();
    Serial.println("Target reached");
    delay(3000);
    nextLocation();
  }
}

void nextLocation() { 
    motorcontrollerxas.disableBrake();
    motorcontrolleryas.disableBrake();
    
    if (locationvisited < 3) {
      if (sizeof(locationQueue) / sizeof(locationQueue[0]) > 1) {
          for (int i = 0; i < sizeof(locationQueue) / sizeof(locationQueue[0]); i++) {
              locationQueue[i][0] = locationQueue[i + 1][0];
              locationQueue[i][1] = locationQueue[i + 1][1];
          }
          locationvisited++;
      }
    } else {
      if (locationvisited >= 3) {
          Serial.println("All locations visited");
          motorcontrollerxas.enableBrake();
          motorcontrolleryas.enableBrake();
          calibrateEncoders();
      }
    }
}

void loadnewpacket(){
  locationQueue[0][0] = 200;
  locationQueue[0][1] = 400;
  locationQueue[1][0] = 1000;
  locationQueue[1][1] = 1000;
  locationQueue[2][0] = 100;
  locationQueue[2][1] = 700; 
}