#ifndef JOYSTICK_H
#define JOYSTICK_H

class Joystick {
public:
    /**
     * Constructor
    */
    Joystick(int yjoystick);

    /**
     * Function that reads the joystick value
    */
    int readJoystick();

    /**
     * Function that sets up the joystick
    */
    void setup();

private:
    int yjoystick;
};

#endif // JOYSTICK_H