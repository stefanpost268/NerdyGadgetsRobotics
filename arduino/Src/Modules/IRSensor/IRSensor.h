#ifndef IRSENSOR_H
#define IRSENSOR_H

class IRSensor {
public:
    // Constructor
    IRSensor();

    // Add your public member functions here
    void vorkForwards(int y);
    void vorkBackwards(int y);

private:
    // motor pins
    int cdirectionPin = 12;
    int cpwmPin = 3;
    int cbrakePin = 9;
    int cencoder1; // Analog encoder
    int cencoder2; // Digital encoder
    bool vorkOpen = false;
};

#endif // IRSENSOR_H