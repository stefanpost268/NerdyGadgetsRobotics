#include "../../../Vendor/ArduinoJson-v7.0.4.h"
#include "../../Enum/RobotStateEnum.h"
#include "../../Enum/LabelEnum.h"

void EmitRobotState(LabelEnum label, RobotStateEnum state){
// Create a DynamicJsonDocument
    DynamicJsonDocument doc(128); // Adjust the size as per your data

    // Add data to the JSON document
    doc["label"] = label;

    // Create an object for the data
    JsonObject data = doc.createNestedObject("data");
    data["state"] = state;
    data["reason"] = state == EMERGENCY_STOP ? "Emergency stop is pressed!" : "";

    // Serialize the JSON document to a string
    String jsonString;
    serializeJson(doc, jsonString);

    // Print the JSON string to the serial monitor
    Serial.println(jsonString);
}
