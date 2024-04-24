#include <Arduino.h>
#include "EmergencyButton.h"
#include "../../../arduino.ino"

EmergencyButton::EmergencyButton(int emergencyStopPin, int resetPin ) {
    this->resetPin = resetPin;
    this->emergencyStopPin = emergencyStopPin;
    pinMode(resetPin, INPUT);
    pinMode(emergencyStopPin, INPUT);
}

void SAFETY() {
  if (digitalRead(emergencyStop) == HIGH) {
    SAFETY_MODE = true;
  }
  if (digitalRead(resetButton) == HIGH && digitalRead(emergencyStop) == LOW) {
    SAFETY_MODE = false;
  }
  if (SAFETY_MODE == true) {
    //stop robot
  }
}


