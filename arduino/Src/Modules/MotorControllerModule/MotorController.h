#ifndef MOTORCONTROLLER_H
#define MOTORCONTROLLER_H

class MotorController {
public:
    /**
     * Constructor
    */
    MotorController(int directionPin, int pwmPin, int brakePin, int encoder1, float speedmultiplier);

    /**
     * Function that drives the motor
    */
    void driveFork(int y, int IRSensorWaarde);
    void driveMotor(int joystickInput, int sensor1, int sensor2, bool SAFETY_MODE, bool vorkOpen);

private:
    int directionPin;
    int pwmPin;
    int brakePin;
    int encoder1;
    float speedmultiplier;


    /**
     * Function that enables the brake
    */
    void enableBrake();
    /**
     * Function that disables the brake
    */
    void disableBrake();
    /**
     * Function that moves the motor forwards
    */
    void motorForwards(int joystickInput);

    /**
     * Function that moves the motor backwards
    */
    void motorBackwards(int joystickInput);

    /**
     * Function that sets up the motor controller
    */
    void setup();
};

#endif // MotorController