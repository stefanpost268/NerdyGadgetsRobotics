#ifndef MOTORCONTROLLER_H
#define MOTORCONTROLLER_H

class MotorController {
public:
    /**
     * Constructor
    */
    MotorController(int directionPin, int pwmPin, int brakePin);

    /**
     * Function that drives the vork
    */
    void driveVork(int y, int IRSensorWaarde, bool EmergencyButtonState);
        /**
     * Function that moves the vork forwards
    */
    void vorkForwards(int y);

    /**
     * Function that moves the vork backwards
    */
    void vorkBackwards(int y);
        /**
     * Function that disables the brake
    */
    void disableBrake();


private:
    int directionPin;
    int pwmPin;
    int brakePin;

    /**
     * Function that enables the brake
    */
    void enableBrake();


    /**
     * Function that sets up the motor controller
    */
    void setup();
};

#endif // MotorController