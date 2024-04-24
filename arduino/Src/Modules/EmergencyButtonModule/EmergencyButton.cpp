#include <Arduino.h>
#include "EmergencyButton.h"

EmergencyButton::EmergencyButton(int emergencyStopPin, int resetPin ): resetPin(resetPin), emergencyStopPin(emergencyStopPin) {
    this->resetPin = resetPin;
    this->emergencyStopPin = emergencyStopPin;
    pinMode(resetPin, INPUT);
    pinMode(emergencyStopPin, INPUT);
}

bool EmergencyButton::isEmergencyStopPressed() {
  return digitalRead(this->emergencyStopPin) == HIGH;
}

bool EmergencyButton::isResetPressed(){
  return digitalRead(this->resetPin) == HIGH;
}
