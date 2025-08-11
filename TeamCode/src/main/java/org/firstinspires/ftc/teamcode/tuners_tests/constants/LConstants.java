package org.firstinspires.ftc.teamcode.tuners_tests.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = 0.0029;
        ThreeWheelConstants.strafeTicksToInches = 0.0029;
        ThreeWheelConstants.turnTicksToInches = 0.0033;
        ThreeWheelConstants.leftY = 7;
        ThreeWheelConstants.rightY = -7;
        ThreeWheelConstants.strafeX = -1.5;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "leftFront_par0";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "rightFront_par1";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "rightBack_perp";
        ThreeWheelConstants.leftEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.rightEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.FORWARD;

    }

}



