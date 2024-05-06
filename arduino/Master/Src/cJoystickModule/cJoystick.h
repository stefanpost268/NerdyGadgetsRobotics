#ifndef CJOYSTICK_H
#define CJOYSTICK_H

class cJoystick {
public:
    /**
     * Constructor
    */
    cJoystick(int cyjoystick);

    /**
     * Function that reads the joystick value
    */
    int readcJoystick();

    /**
     * Function that sets up the joystick
    */
    void setup();

private:
    int cyjoystick;
};

#endif // CJOYSTICK_H