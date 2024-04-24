#include <Arduino.h>
#include <Wire.h>

#define SLAVE_ADDRESS 9 // Address of the slave Arduino

// motor pins
int cdirectionPin = 12;
int cpwmPin = 3;
int cbrakePin = 9;
int cencoder1; // Analog encoder
int cencoder2; // Digital encoder

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
    // motor pins
    pinMode(cdirectionPin, OUTPUT);
    pinMode(cpwmPin, OUTPUT);
    pinMode(cbrakePin, OUTPUT);
    pinMode(cencoder1, INPUT);
    pinMode(cencoder2, INPUT);

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

void vorkForward(int y)
{
    digitalWrite(cdirectionPin, LOW);
    analogWrite(cpwmPin, abs(y));
}

void vorkBackward(int y)
{
    digitalWrite(cdirectionPin, HIGH);
    analogWrite(cpwmPin, y);
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

void driveVork(int y) {
    if (y < -50 && IR1 > 140)
    {
        digitalWrite(cbrakePin, LOW);
        vorkForward(y);
    }
    else if (y > 50  && IR1 < 390)
    {
        digitalWrite(cbrakePin, LOW);
        vorkBackward(y);
    }
    else {
        digitalWrite(cbrakePin, HIGH);
    }
}