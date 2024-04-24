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
int cyjoystick = A2;

// IRSensor pin
int cIRSensor;

// cINsensor pin
int cINSensor1;
int cINSensor2;

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

    // IRSensor pins
    pinMode(cIRSensor, INPUT);
    pinMode(cINSensor1, INPUT);

    // Initialize I2C communications as Master
    Wire.begin();

    Serial.begin(9600);
}

void loop()
{
    if (analogRead(cIRSensor) < 100) {
        sendVorkStateToSlave(vorkOpen);
    }

    // read joystick values
    y = analogRead(cyjoystick);
    y = map(y, 0, 1023, -254, 255);

    // read IR & IN sensors
    IR1 = analogRead(cIRSensor);

    // initialize vork movement
    driveVork(y);
}

void vorkForward(int y)
{
    digitalWrite(cdirectionPin, HIGH);
    analogWrite(cpwmPin, y);
}

void vorkBackward(int y)
{
    digitalWrite(cdirectionPin, LOW);
    analogWrite(cpwmPin, abs(y));
}

void sendVorkStateToSlave(bool b) {
  Wire.beginTransmission(SLAVE_ADDRESS);    // Start communication with slave
  Wire.write(b);                            // Send the command to the slave
  Wire.endTransmission();                   // End transmission

  Serial.print("Vork open: ");
  Serial.println(b);
}

void driveVork(int y) {
    if (y > 50)
    {
        digitalWrite(cbrakePin, LOW);
        vorkForward(y);
    }
    else if (y < -50)
    {
        digitalWrite(cbrakePin, LOW);
        vorkBackward(y);
    }
    else {
        digitalWrite(cbrakePin, HIGH);
    }
}