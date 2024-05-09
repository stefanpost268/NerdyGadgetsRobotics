#ifndef INDUCTIVE_SENSOR_H
#define INDUCTIVE_SENSOR_H

class InductiveSensor {
    public:
        InductiveSensor(int InductiveSensorPin);
        int readInductiveSensor();
        
    private:
        int InductiveSensorPin;
};

#endif // INDUCTIVE_SENSOR_H