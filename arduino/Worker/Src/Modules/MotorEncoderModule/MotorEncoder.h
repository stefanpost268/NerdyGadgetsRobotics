#ifndef MOTORENCODER_H
#define MOTORENCODER_H

class MotorEncoder {
public:
    /**
     * Constructor
    */
    MotorEncoder(int encoder1Pin, int encoder2Pin);

    /**
     * Function that reads the encoder value
    */
    void readEncoder();

    /**
     * Function that returns the motor location
    */
    volatile int getMotorLocation();

    /**
     * Function that returns the motor location as a coordinate
    */
    int getMotorLocationAsCoordinate(int max, int columnCount);

    /**
     * Resets encoder value
    */
    void resetEncoder();

    /**
     * Calibrates the encoders
    */
    void calibrate();


private:
    int encoder1Pin;
    int encoder2Pin;
    volatile int motorLocation;

    void setup();
};

#endif // MOTORENCODER_H