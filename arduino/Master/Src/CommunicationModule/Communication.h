#include "../IRSensorModule/IRSensor.h"
#include "../MotorEncoderModule/MotorEncoder.h"

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
    void sendInformationToWorker(String label, String data);

    /**
     * Function that reads the vork state
    */
    bool readVorkState(IRSensor sensor);

private:
    bool vorkOpen;
    int workerAdress;
};

#endif // COMMUNICATION_H