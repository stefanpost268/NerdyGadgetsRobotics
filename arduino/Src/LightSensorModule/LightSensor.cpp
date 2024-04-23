#include <Arduino.h>
#include "LightSensor.h"
#include "../../Modules/ArduinoJson-v7.0.4.h"

LightSensor::LightSensor(int pin) {
    this->pin = pin;
    pinMode(pin, INPUT);
}

bool LightSensor::isActive() {
    return analogRead(pin) > 100;
}

void LightSensor::emitWarehouseTiltedStatus() {
    DynamicJsonDocument doc(128);
    doc["label"] = "state";
    JsonObject data = doc.createNestedObject("data");
    data["state"] = "SAFETY_MODE";
    data["reason"] = "The warehouse has tilted";
    String jsonString;
    serializeJson(doc, jsonString);
    Serial.println(jsonString);
}