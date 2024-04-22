#include "Src/LightSensorModule/LightSensor.h"

LightSensor lightSensor = LightSensor(A0);

const int sensorPin = A0;

void setup() {
  Serial.begin(9600);
}

void loop() {
  Serial.println(lightSensor.isActive());
  delay(1000);
}
