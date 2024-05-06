#include <Arduino.h>
#include <Wire.h>
#include "./Src/IRSensorModule/IRSensor.h"
#include "./Src/cMotorControllerModule/cMotorController.h"
#include "./Src/cJoystickModule/cJoystick.h"
#include "./Src/CommunicationModule/Communication.h"

// IRSensor pin
IRSensor sensor = IRSensor(A2);
cMotorController motorcontroller = cMotorController(12, 3, 9, NULL, NULL);
cJoystick joystick = cJoystick(A3);
Communication communication = Communication(9);

// variables

void setup()
{
    Serial.begin(9600);
}

void loop()
{
    communication.sendVorkStateToWorker(sensor);
    
    motorcontroller.driveVork(
        joystick.readcJoystick(),
        sensor.readIRSensor()
    );
}