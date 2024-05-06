#ifndef IRSENSOR_H
#define IRSENSOR_H

class IRSensor {
public:
    /**
     * Constructor
    */
    IRSensor(int cIRSensor);

    /**
     * Function that reads the IR sensor
    */
    int readIRSensor();

private:
    int cIRSensor;
    
    /**
     * Function that sets up the IR sensor
    */
    void setup();
};

#endif // IRSENSOR_H