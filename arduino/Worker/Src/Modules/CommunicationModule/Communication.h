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
    void sendInformationToMaster(String label, String data);


private:
    bool vorkOpen;
    int workerAdress;
};

#endif // COMMUNICATION_H