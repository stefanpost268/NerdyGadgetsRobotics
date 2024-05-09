#include <Arduino.h>
#include "Joystick.h"

Joystick::Joystick(int yjoystick) {
    this->yjoystick = yjoystick;
    setup();
}

void Joystick::setup() {
    pinMode(yjoystick, INPUT);
}

int Joystick::readJoystick() {
    return map(analogRead(yjoystick), 0, 1023, -254, 255);
}
