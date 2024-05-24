#include "../IRSensorModule/IRSensor.h"

#ifndef COMMUNICATION_H
#define COMMUNICATION_H

class Communication {
public:
    /**
     * Constructor
    */
    Communication(int workerAdress);

    /**
     * Function that sends the vork state to the worker arduino
    */
    void sendInformationToWorker(int Data[], IRSensor sensor, MotorEncoder motorencoder);

private:
    bool vorkOpen;
    int workerAdress;
    int Data[2];
    /**
     * Function that reads the vork state
    */
    bool readVorkState(IRSensor sensor);
};

#endif // COMMUNICATION_H