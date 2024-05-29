#ifndef MOTORCONTROLLER_H
#define MOTORCONTROLLER_H

class MotorController {
public:
    /**
     * Constructor
    */
    MotorController(int directionPin, int pwmPin, int brakePin, float speedmultiplier);

    /**
     * Function that drives the motor
    */
    void driveFork(int y, int IRSensorWaarde);
    
    /**
     * Drive the motor
    */
    void driveMotor(int joystickInput, int sensor1, int sensor2, bool SAFETY_MODE, bool vorkOpen);
    
    /**
     * Function that stops the motor
    */
    void emergencyStop();

    /**
     * Function that gets the motor location
    */
    int getMotorLocation();

    /**
     * Get current motor coordinate based on motor location
     * @param max The maximum value of the coordinate its important that this is the coordinate of the motor at the end of the warehouse
     * @param columnCount The amount of columns in the warehouse
    */
    int getMotorLocationAsCoordinate(int max, int columnCount);

    /**
     * Function that reads the encoder and sets the motor location
    */
    void readEncoder();

    void goToLocation(int currentxInput, int currentyInput, int targetx, int targety);

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

private:
    int directionPin;
    int pwmPin;
    int brakePin;
    float speedmultiplier;

    /**
     * Function that sets up the motor controller
    */
    void setup();
};

#endif // MotorController