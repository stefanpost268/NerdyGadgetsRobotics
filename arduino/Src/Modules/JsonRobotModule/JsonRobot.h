
#ifndef JSONROBOT_H
#define JSONROBOT_H

#include "../../Enum/RobotStateEnum.h"
#include "../../Enum/LabelEnum.h"

class JsonRobot {
    public:
        void emitRobotState(LabelEnum x , RobotStateEnum t, String reason );      
    private:

};

#endif