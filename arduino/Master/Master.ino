#include <Arduino.h>
#include <Wire.h>
#include "./Src/IRSensorModule/IRSensor.h"
#include "./Src/MotorControllerModule/MotorController.h"
#include "./Src/JoystickModule/Joystick.h"
#include "./Src/CommunicationModule/Communication.h"
#include "./Src/MotorEncoderModule/MotorEncoder.h"
#include "./Src/EmergencyButtonModule/EmergencyButton.h"

IRSensor sensor = IRSensor(A2);
MotorController motorcontroller = MotorController(12, 3, 9);
MotorEncoder motorencoder = MotorEncoder(2, 4);
Joystick joystick = Joystick(A3);
Communication communication = Communication(9);
EmergencyButton emergencyButton = EmergencyButton(10);

bool EmergencyButtonState = false;
int communicationData[] = {sensor.readIRSensor(), motorencoder.getMotorLocation()};
bool vorkCurrentState = false;
int currentLocation = 0;

bool goForward = false;
bool goBackward = false;
bool fully_extended = false;


void setup()
{
    Serial.begin(9600);
    Wire.begin(9);
    Wire.onReceive(receiveEvent);
    attachInterrupt(digitalPinToInterrupt(2), []() {
        motorencoder.readEncoder();
    }, RISING);
}

void loop()
{
    Wire.requestFrom(9, 1);
    if(Wire.available())
    {
        EmergencyButtonState = Wire.read();
    } 

    bool state = communication.readVorkState(sensor);
    int location = motorencoder.getMotorLocation();
    if(vorkCurrentState != state) {
        vorkCurrentState = state;
        communication.sendInformationToWorker("v", (String) vorkCurrentState);
    }

    if (millis() % 50 == 0 && location != currentLocation) {
        currentLocation = location;
        communication.sendInformationToWorker("y", (String) currentLocation);
    }

    if(millis() % 200 == 0 && emergencyButton.isResetPressed()) {
        communication.sendInformationToWorker("r", "1");
    }

    motorcontroller.driveVork(
        joystick.readJoystick(),
        sensor.readIRSensor(),
        EmergencyButtonState
    );

    pickUpProduct();
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

    // Serial.println("received: "+ value);     

    if(label == "p") {
        if (value == "VORK_FORWARD")
        {
            goForward = true;
        }
        if (value == "VORK_BACKWARD")
        {
            goBackward = true;
        }
        
  } 
}

void pickUpProduct() {    
    if (goForward)
    {
        motorcontroller.disableBrake();
        motorcontroller.vorkForwards(255);
        if (sensor.readIRSensor() < 160)
        {
            goForward = false;
            communication.sendInformationToWorker("p", "FULLY_EXTENDED");
        }
        

    }
        if (goBackward)
    {
        motorcontroller.disableBrake();
        motorcontroller.vorkBackwards(255);
        if (sensor.readIRSensor() > 400)
        {
            goBackward = false;
        }
        

    }
    
}

//fully retracted:408 -------fully extended:152