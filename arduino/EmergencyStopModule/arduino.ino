
#define LED 4
#define emergencyStop 8
#define resetButton 10

bool SAFETY_MODE = false;
int buttonState = digitalRead(emergencyStop);
void LEDstate() {
  if (SAFETY_MODE) {
    turnOnLED();
  } else {
    turnOffLED();
  }
}

void turnOnLED() {
  digitalWrite(LED, HIGH);
}
void turnOffLED() {
  digitalWrite(LED, LOW);
}


void setup() {
  pinMode(LED, OUTPUT);
  pinMode(resetButton, INPUT_PULLUP);
  pinMode(emergencyStop, INPUT_PULLUP);
  Serial.begin(9600);
}

void loop() {
  SAFETY();
}


void SAFETY() {
  LEDstate();

  if (digitalRead(emergencyStop) == HIGH) {
    SAFETY_MODE = true;
  }

  if (digitalRead(resetButton) == HIGH && digitalRead(emergencyStop) == LOW) {
    SAFETY_MODE = false;
  }

  if (!Serial.available()) {
    SAFETY_MODE = true;
  }

  if (SAFETY_MODE == true) {
    //stop robot
  }
}
