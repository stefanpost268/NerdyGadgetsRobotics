#ifndef CMOTORCONTROLLER_H
#define CMOTORCONTROLLER_H

class cMotorController {
public:
    /**
     * Constructor
    */
    cMotorController(int cdirectionPin, int cpwmPin, int cbrakePin, int cencoder1, int cencoder2);

    /**
     * Function that drives the vork
    */
    void driveVork(int y, int IR1);

private:
    int cdirectionPin;
    int cpwmPin;
    int cbrakePin;
    int cencoder1;
    int cencoder2;

    /**
     * Function that enables the brake
    */
    void enableBrake();
    /**
     * Function that disables the brake
    */
    void disableBrake();
    /**
     * Function that moves the vork forwards
    */
    void vorkForwards(int y);

    /**
     * Function that moves the vork backwards
    */
    void vorkBackwards(int y);

    /**
     * Function that sets up the motor controller
    */
    void setup();
};

#endif // cMotorController