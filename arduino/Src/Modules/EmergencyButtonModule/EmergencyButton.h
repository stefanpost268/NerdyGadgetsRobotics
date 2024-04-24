#ifndef EMERGENCYBUTTON_H
#define EMERGENCYBUTTON_H

class EmergencyButton {
    public:
        EmergencyButton(int emergencyStopPin, int resetPin);
    private:
        int resetPin;
        int emergencyStopPin;
};

#endif