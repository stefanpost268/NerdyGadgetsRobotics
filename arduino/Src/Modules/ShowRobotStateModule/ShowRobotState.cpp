#include <Arduino.h>
#include "ShowRobotState.h"

ShowRobotState::ShowRobotState(int redPin, int greenPin, int bluePin, int SwitchToAutomatic): redPin(redPin), greenPin(greenPin), bluePin(bluePin), SwitchToAutomatic(SwitchToAutomatic){
    this->redPin = redPin;
    this->greenPin = greenPin;
    this->bluePin = bluePin;
    this->SwitchToAutomatic = SwitchToAutomatic;
    pinMode(redPin, OUTPUT);
    pinMode(greenPin, OUTPUT);
    pinMode(bluePin, OUTPUT);
    pinMode(SwitchToAutomatic, INPUT);
}

void ShowRobotState::setColor(int redValue, int greenValue, int blueValue){
  analogWrite(redPin, redValue);
  analogWrite(greenPin, greenValue);
  analogWrite(bluePin, blueValue);
}

bool ShowRobotState::IsSwitchToAutomaticPressed(){
    return digitalRead(this->SwitchToAutomatic) == LOW;
}