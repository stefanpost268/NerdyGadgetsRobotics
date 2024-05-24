#include <Arduino.h>
#include <Wire.h>
#include "./Src/IRSensorModule/IRSensor.h"
#include "./Src/MotorControllerModule/MotorController.h"
#include "./Src/JoystickModule/Joystick.h"
#include "./Src/CommunicationModule/Communication.h"
#include "./Src/MotorEncoderModule/MotorEncoder.h"

IRSensor sensor = IRSensor(A2);
MotorController motorcontroller = MotorController(12, 3, 9);
MotorEncoder motorencoder = MotorEncoder(2, 4);
Joystick joystick = Joystick(A3);
Communication communication = Communication(9);

bool EmergencyButtonState = false;
int communicationData[] = {sensor.readIRSensor(), motorencoder.getMotorLocation()};
bool vorkCurrentState = false;
int currentLocation = 0;


void setup()
{
    Serial.begin(9600);
    attachInterrupt(digitalPinToInterrupt(2), []() {
        motorencoder.readEncoder();
    }, RISING);
}

void loop()
{
    Wire.requestFrom(9, 1);

    bool state = communication.readVorkState(sensor);
    int location = motorencoder.getMotorLocation();
    if(vorkCurrentState != state) {
        vorkCurrentState = state;
        communication.sendInformationToWorker("v", (String) vorkCurrentState);
    }

    //if (millis() % 50 == 0 && location != currentLocation) {
        currentLocation = location;
        communication.sendInformationToWorker("y", (String) currentLocation);
    //}

    motorcontroller.driveVork(
        joystick.readJoystick(),
        sensor.readIRSensor(),
        EmergencyButtonState
    );

    Serial.println(currentLocation);
}