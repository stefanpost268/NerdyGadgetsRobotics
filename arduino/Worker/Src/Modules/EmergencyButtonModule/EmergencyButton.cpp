#include <Arduino.h>
#include "EmergencyButton.h"

EmergencyButton::EmergencyButton(int resetPin): emergencyPin(emergencyPin) {
    this->emergencyPin = emergencyPin;
    pinMode(emergencyPin, INPUT);
}

bool EmergencyButton::isEmergencyStop(){
  return digitalRead(this->emergencyPin) == HIGH;
}
