// #include <Arduino_JSON.h>

// motor pins
int cdirectionPin = 12;
int cpwmPin = 3;
int cbrakePin = 9;
int cencoder1 = A0; // Analog encoder
int cencoder2 = A1; // Digital encoder

// joystick pins
int cxjoystick = A2;
int cyjoystick = A3;

// IRSensor pin
int cIRSensor = A4;

// cINsensor pin
int cINSensor1 = A5;

// variables
int x;
int y;
int IR1;

void setup()
{
    // motor pins
    pinMode(cdirectionPin, OUTPUT);
    pinMode(cpwmPin, OUTPUT);
    pinMode(cbrakePin, OUTPUT);
    pinMode(cencoder1, INPUT);
    pinMode(cencoder2, INPUT);

    // joystick pins
    pinMode(cxjoystick, INPUT);
    pinMode(cyjoystick, INPUT);

    // IRSensor pins
    pinMode(cIRSensor, INPUT);
    pinMode(cINSensor1, INPUT);

    Serial.begin(9600);
}

void loop()
{
    Serial.println(analogRead(cINSensor1));

    // read joystick values
    x = analogRead(A2);
    y = analogRead(A3);
    x = map(x, 0, 1023, -255, 255);
    y = map(y, 0, 1023, -255, 255);

    // read IR sensors
    IR1 = analogRead(cIRSensor);

    if (x >= 0)
    {
        vorkForward(x);
    }
    else
    {
        vorkBackward(x);
    }
}

void vorkForward(int x)
{
    digitalWrite(cdirectionPin, HIGH);
    analogWrite(cpwmPin, x);
}

void vorkBackward(int x)
{
    digitalWrite(cdirectionPin, LOW);
    analogWrite(cpwmPin, abs(x));
}