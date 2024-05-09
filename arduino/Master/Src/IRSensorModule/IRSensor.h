#ifndef IRSENSOR_H
#define IRSENSOR_H

class IRSensor {
public:
    /**
     * Constructor
    */
    IRSensor(int IRSensorPin);

    /**
     * Function that reads the IR sensor
    */
    int readIRSensor();

private:
    int IRSensorPin;
    
    /**
     * Function that sets up the IR sensor
    */
    void setup();
};

#endif // IRSENSOR_H