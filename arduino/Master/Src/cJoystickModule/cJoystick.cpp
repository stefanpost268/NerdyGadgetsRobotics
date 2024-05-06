#include <Arduino.h>
#include "cJoystick.h"

cJoystick::cJoystick(int cyjoystick) {
    this->cyjoystick = cyjoystick;
    setup();
}

void cJoystick::setup() {
    pinMode(cyjoystick, INPUT);
}

int cJoystick::readcJoystick() {
    return map(analogRead(cyjoystick), 0, 1023, -254, 255);
}
