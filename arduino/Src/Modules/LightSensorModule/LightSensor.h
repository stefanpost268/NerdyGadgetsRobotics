#ifndef LIGHTSENSOR_H
#define LIGHTSENSOR_H

class LightSensor {
    public:
        LightSensor(int pin);
        bool isActive();
    private:
        int pin;
};

#endif
