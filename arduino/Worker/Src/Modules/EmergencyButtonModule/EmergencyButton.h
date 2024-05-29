#ifndef EMERGENCYBUTTON_H
#define EMERGENCYBUTTON_H

class EmergencyButton {
    public:
        EmergencyButton(int resetPin);
        bool isEmergencyStop();
    private:
        int emergencyPin;
};

#endif