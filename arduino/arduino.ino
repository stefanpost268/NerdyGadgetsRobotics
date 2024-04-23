#include "Src/LightSensorModule/LightSensor.h"

LightSensor lightSensor = LightSensor(A0);
bool safetyMode = false;

void setup() {
  Serial.begin(9600);
}

void loop() {
  if(safetyMode) {
    return;
  }

  if (!lightSensor.isActive()) {
    lightSensor.emitWarehouseTiltedStatus();
    safetyMode = true;
  }
}
