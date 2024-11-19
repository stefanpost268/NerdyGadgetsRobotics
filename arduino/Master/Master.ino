#include <Arduino.h>
#include <Wire.h>
#include "./Src/IRSensorModule/IRSensor.h"
#include "./Src/MotorControllerModule/MotorController.h"
#include "./Src/JoystickModule/Joystick.h"
#include "./Src/CommunicationModule/Communication.h"
#include "./Src/MotorEncoderModule/MotorEncoder.h"
#include "./Src/EmergencyButtonModule/EmergencyButton.h"
#include "./Src/ShowRobotStateModule/ShowRobotState.h"

IRSensor sensor = IRSensor(A2);
MotorController motorcontroller = MotorController(12, 3, 9);
MotorEncoder motorencoder = MotorEncoder(2, 4);
Joystick joystick = Joystick(A3);
Communication communication = Communication(9);
EmergencyButton emergencyButton = EmergencyButton(10);
ShowRobotState showRobotState = ShowRobotState(5, 6, 7, 8); 

bool EmergencyButtonState = false;
int communicationData[] = {sensor.readIRSensor(), motorencoder.getMotorLocation()};
bool vorkCurrentState = false;
int currentLocation = 0;
bool redLEd = false;
bool greenLed = false;
bool orangeLed = true;
bool switchstate = false;
void setup()
{

    Serial.begin(9600);
    attachInterrupt(digitalPinToInterrupt(2), []() {
        motorencoder.readEncoder();
    }, RISING);

    Wire.begin(9); // Initialize I2C communication with address
    Wire.onReceive(receiveEvent); // Set up a function to handle received data
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


if(digitalRead(8) == HIGH){
    communication.sendInformationToWorker("a", "1");
    greenLed = true;
    redLEd = false;
    orangeLed = false;
}
    
    if(redLEd){
    showRobotState.setColor(255,0,0);// rood
    }else if(greenLed){
    showRobotState.setColor(0,255,0);// groen
    }else if(orangeLed){
    showRobotState.setColor(255,140,0);// oranje
    }

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

  Serial.println(message);

  if(label == "s") {
    redLEd = true;
    orangeLed = false;
    greenLed = false;
  }
  if(label == "m") {
    orangeLed = true;
    redLEd = false;
    greenLed = false;
  }
}
