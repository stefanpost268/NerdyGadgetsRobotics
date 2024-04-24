#include "../arduino/Src/Modules/LightSensorModule/LightSensor.h"
#include "Vendor/ArduinoJson-v7.0.4.h"
#include "Src/Modules/JsonRobotModule/JsonRobot.cpp"
#include "../arduino/Src/Enum/RobotStateEnum.h"
#include "../arduino/Src/Enum/LabelEnum.h"
#include  "../arduino/Src/Modules/EmergencyButtonModule/EmergencyButton.cpp"

// EmergencyStop
#define emergencyStop 8
#define resetButton 10

bool SAFETY_MODE = false;

// lightSensor
LightSensor lightSensor = LightSensor(A0);
bool messageSent = false; // Flag to track if the message has been sent

const int sensorPin = A0;

void setup() {
  pinMode(resetButton, INPUT_PULLUP);
  pinMode(emergencyStop, INPUT_PULLUP);
  Serial.begin(9600);
}

void loop() {


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
  if (digitalRead(emergencyStop) == HIGH && !SAFETY_MODE && !digitalRead(resetButton)) { 
    EmitRobotState(STATE, EMERGENCY_STOP);
  }

  // MANUAL
  if (digitalRead(resetButton) == HIGH && SAFETY_MODE && !digitalRead(emergencyStop)) { 
   EmitRobotState(STATE, MANUAL_MODE);
  }

}



