#ifndef INDUCTIVE_SENSOR_H
#define INDUCTIVE_SENSOR_H

class InductiveSensor {
    public:
        InductiveSensor(int InductiveSensorPin);
        
        /**
         * Read the inductive sensor, returns false when object detected.
        */
        bool readInductiveSensor();
        
    private:
        int InductiveSensorPin;
};

#endif // INDUCTIVE_SENSOR_H