#include <Arduino.h>
#include "EmergencyButton.h"

EmergencyButton::EmergencyButton(int resetPin): resetPin(resetPin) {
    this->resetPin = resetPin;
    pinMode(resetPin, INPUT);
}

bool EmergencyButton::isResetPressed(){
  return digitalRead(this->resetPin) == HIGH;
}
