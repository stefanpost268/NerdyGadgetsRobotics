#include <Arduino.h>
#include <Wire.h>
#include "./Src/IRSensorModule/IRSensor.h"
#include "./Src/MotorControllerModule/MotorController.h"
#include "./Src/JoystickModule/Joystick.h"
#include "./Src/CommunicationModule/Communication.h"
#include "./Src/MotorEncoderModule/MotorEncoder.h"

IRSensor sensor = IRSensor(A2);
MotorController motorcontroller = MotorController(12, 3, 9);
MotorEncoder motorencoder = MotorEncoder(2, 5);
Joystick joystick = Joystick(A3);
Communication communication = Communication(9);

bool EmergencyButtonState = false;
int communicationData[] = {sensor.readIRSensor(), motorencoder.getMotorLocation()};

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
    if(Wire.available())
    {
        EmergencyButtonState = Wire.read();
    } 

    communication.sendInformationToWorker(communicationData, sensor, motorencoder);

    
    motorcontroller.driveVork(
        joystick.readJoystick(),
        sensor.readIRSensor(),
        EmergencyButtonState
    );

    Serial.println(motorencoder.getMotorLocationAsCoordinate(2000, 5));
    Serial.print("Motor location y: ");
    Serial.println(motorencoder.getMotorLocation());
    
}