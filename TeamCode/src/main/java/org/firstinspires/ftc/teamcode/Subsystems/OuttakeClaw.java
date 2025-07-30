package org.firstinspires.ftc.teamcode.Subsystems;


import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil;
import com.rowanmcalpin.nextftc.core.control.controllers.Controller;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MotorHoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.ResetEncoder;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.pedro.DisplacementDelay;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
/*public class OuttakeClaw extends Subsystem {
    public static final OuttakeClaw INSTANCE = new OuttakeClaw();

    public Servo outtakeGripServo;

    private enum OuttakeGripState {OPEN, CLOSED}


    private OuttakeGripState GripState = OuttakeGripState.CLOSED;

    private static final double GRIP_OPEN_POS = 0.48;
    private static final double GRIP_CLOSED_POS = 0.15;

    public String Ogrip = "outtake_grip";


    //CLAW COMMANDS
    public Command openClaw() {
        GripState = OuttakeGripState.OPEN;
        return new ServoToPosition(outtakeGripServo, GRIP_OPEN_POS, this);
    }


    public Command closeClaw() {
        GripState = OuttakeGripState.CLOSED;
        return new ServoToPosition(outtakeGripServo, GRIP_CLOSED_POS, this);
    }


    public Command toggleClaw() {
        if (GripState == OuttakeGripState.OPEN) {

            return closeClaw();


        } else {

            return Outtake.INSTANCE.preTransfer();
        }
    }
    @Override
    public void initialize() {
        outtakeGripServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Ogrip);
    }
}*/
