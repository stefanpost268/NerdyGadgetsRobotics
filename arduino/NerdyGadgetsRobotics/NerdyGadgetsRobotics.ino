#include <Arduino.h>
#include <Wire.h>

#define SLAVE_ADDRESS 9 // Address of the slave Arduino

// joystick pins
int cyjoystick = A3;

// IRSensor pin
int cIRSensor = A2;

// variables
int y;
int IR1;
bool vorkOpen = false;

void setup()
{
    // joystick pin
    pinMode(cyjoystick, INPUT);

    // IRSensor pin
    pinMode(cIRSensor, INPUT);

    // Initialize I2C communications as Master
    Wire.begin();

    Serial.begin(9600);
}

void loop()
{
    // send vork state to slave
    sendVorkStateToSlave(vorkOpen);

    // read joystick values
    y = analogRead(cyjoystick);
    y = map(y, 0, 1023, -254, 255);

    // read IR sensor value
    IR1 = analogRead(cIRSensor);
    
    // initialize vork movement
    driveVork(y);    
}

void sendVorkStateToSlave(bool b) {
  if (analogRead(cIRSensor) > 380) {
    b = true;
  } else {
    b = false;
  }
  
  Wire.beginTransmission(SLAVE_ADDRESS);    // Start communication with slave
  Wire.write(b);                            // Send the command to the slave
  Wire.endTransmission();                   // End transmission

  Serial.print("Vork open: ");
  Serial.println(b);
}