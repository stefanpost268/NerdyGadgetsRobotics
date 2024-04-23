#include "Src/LightSensorModule/LightSensor.h"
#include "Modules/ArduinoJson-v7.0.4.h"

// emergencyStop
#include <ArduinoJson.h>
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

// lightSensor
LightSensor lightSensor = LightSensor(A0);
bool messageSent = false; // Flag to track if the message has been sent

const int sensorPin = A0;

void setup() {
  pinMode(LED, OUTPUT);
  pinMode(resetButton, INPUT_PULLUP);
  pinMode(emergencyStop, INPUT_PULLUP);
  Serial.begin(9600);
}

void loop() {
SAFETY();
  if (!lightSensor.isActive() && !messageSent) { // Check if the sensor is inactive and the message hasn't been sent
    // Create a DynamicJsonDocument
    DynamicJsonDocument doc(128); // Adjust the size as per your data

    // Add data to the JSON document
    doc["label"] = "state";

    // Create an object for the data
    JsonObject data = doc.createNestedObject("data");
    data["state"] = "SAFETY_MODE";
    data["reason"] = "Light sensor is active";

    // Serialize the JSON document to a string
    String jsonString;
    serializeJson(doc, jsonString);

    // Print the JSON string to the serial monitor
    Serial.println(jsonString);

    messageSent = true; // Set the flag to indicate that the message has been sent
  }

//emergencyStop
if (!SAFETY_MODE && !messageSent) { // Check if the sensor is inactive and the message hasn't been sent
    // Create a DynamicJsonDocument
    DynamicJsonDocument doc(128); // Adjust the size as per your data

    // Add data to the JSON document
    doc["label"] = "state";

    // Create an object for the data
    JsonObject data = doc.createNestedObject("data");
    data["state"] = "SAFETY_MODE";
    data["reason"] = "emergency button was pressed";

    // Serialize the JSON document to a string
    String jsonString;
    serializeJson(doc, jsonString);

    // Print the JSON string to the serial monitor
    Serial.println(jsonString);

    messageSent = true; // Set the flag to indicate that the message has been sent
  }

  delay(1000);
}


void SAFETY() {
  LEDstate();
  if (digitalRead(emergencyStop) == HIGH) {
    SAFETY_MODE = true;
  }
  if (digitalRead(resetButton) == HIGH && digitalRead(emergencyStop) == LOW) {
    SAFETY_MODE = false;
  }
  if (!Serial.available()) { //json implementeren?
    SAFETY_MODE = true;
  }
  if (SAFETY_MODE == true) {
    //stop robot
  }
}

