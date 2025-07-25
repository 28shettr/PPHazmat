package org.firstinspires.ftc.teamcode.Subsystems;


import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil;
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


public class OuttakeSlides extends Subsystem {
    public static final OuttakeSlides INSTANCE = new OuttakeSlides();
    public MotorEx outtakeSlideLeft, outtakeSlideRight;
    public MotorGroup outtakeMotor;

    private OuttakeSlidesState SlidesState = OuttakeSlidesState.TRANSFER;
    private enum OuttakeSlidesState {LOW_BUCKET, HIGH_BUCKET, TRANSFER}

    private static final double OUTTAKESLIDES_TRANSFER_POS = 0;
    private static final double OUTTAKESLIDES_highBUCKET_POS = 1350;
    private static final double OUTTAKESLIDES_lowBUCKET_POS = 200;

    public String OslideLeft = "outtake_slides_left";
    public String OslideRight = "outtake_slides_right";

    public PIDFController controller = new PIDFController(0.005, 0.0, 0.0, new StaticFeedforward(0.0));


    public Command outtakeSlidesTransfer() {
        SlidesState = OuttakeSlidesState.TRANSFER;
        return new RunToPosition(outtakeMotor, // MOTOR TO MOVE
                OUTTAKESLIDES_TRANSFER_POS, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM


    }



    public Command lowBucket() {
        SlidesState = OuttakeSlidesState.LOW_BUCKET;
        return new RunToPosition(outtakeMotor, // MOTOR TO MOVE
                OUTTAKESLIDES_lowBUCKET_POS, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }


    public Command highBucket() {
        SlidesState = OuttakeSlidesState.HIGH_BUCKET;
        return new RunToPosition(outtakeMotor, // MOTOR TO MOVE
                OUTTAKESLIDES_highBUCKET_POS, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }
    public Command preTransfer() {
        return new SequentialGroup(
                new HoldPosition(outtakeMotor, controller , this),
                Outtake.INSTANCE.openClaw(),

                new Delay(TimeSpan.fromSec(1.3)),
                Outtake.INSTANCE.wristTransfer(),

                Outtake.INSTANCE.armTransfer(),
                OuttakeSlides.INSTANCE.outtakeSlidesTransfer()




        );
    }
    @Override
    public void initialize() {
        outtakeMotor = new MotorGroup(new MotorEx(OslideLeft).reverse(), new MotorEx(OslideRight));

    }
}