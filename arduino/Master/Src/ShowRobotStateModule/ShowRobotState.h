#ifndef SHOWROBOTSTATE_H
#define SHOWROBOTSTATE_H

class ShowRobotState{
    public:
        ShowRobotState(int redPin, int greenPin, int bluePin, int SwitchToAutomatic);
        void setColor(int redPin, int greenPin, int bluePin);
        bool IsSwitchToAutomaticPressed();
    private:
        int redPin;
        int greenPin;
        int bluePin;
        int SwitchToAutomatic;
};

#endif