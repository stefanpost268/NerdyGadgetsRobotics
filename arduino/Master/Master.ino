#include <Arduino.h>
#include <Wire.h>
#include "./Src/IRSensorModule/IRSensor.h"
#include "./Src/MotorControllerModule/MotorController.h"
#include "./Src/JoystickModule/Joystick.h"
#include "./Src/CommunicationModule/Communication.h"

IRSensor sensor = IRSensor(A2);
MotorController motorcontroller = MotorController(12, 3, 9, A1);
Joystick joystick = Joystick(A3);
Communication communication = Communication(9);

void setup()
{
    Serial.begin(9600);
}

void loop()
{
    communication.sendVorkStateToWorker(sensor);
    
    motorcontroller.driveVork(
        joystick.readJoystick(),
        sensor.readIRSensor()
    );
}