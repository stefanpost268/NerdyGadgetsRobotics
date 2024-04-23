#include "Src/LightSensorModule/LightSensor.h"
#include "Modules/ArduinoJson-v7.0.4.h"

LightSensor lightSensor = LightSensor(A0);
bool messageSent = false; // Flag to track if the message has been sent

const int sensorPin = A0;

void setup() {
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

  delay(1000);
}
