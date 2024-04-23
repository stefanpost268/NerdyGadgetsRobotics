#ifndef LIGHSENSOR_H
#define LIGHSENSOR_H

class LightSensor {
    public:
        LightSensor(int pin);
        bool isActive();
        void emitWarehouseTiltedStatus();
    private:
        int pin;
};

#endif
