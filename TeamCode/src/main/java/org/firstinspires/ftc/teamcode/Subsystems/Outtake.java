package org.firstinspires.ftc.teamcode.Subsystems;


import androidx.annotation.NonNull;

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


public class Outtake extends Subsystem {
    public static final Outtake INSTANCE = new Outtake();
    public Servo outtakeArmServo, outtakeWristServo, outtakeGripServo;
    public SensorColor colorSensor;










    private enum OuttakeGripState {OPEN, CLOSED}


    private OuttakeGripState GripState = OuttakeGripState.CLOSED;


   private enum OuttakeWristState {DROP, TRANSFER, INIT, PARK}

    private OuttakeWristState WristState = OuttakeWristState.INIT;




 private OuttakeArmState ArmState = OuttakeArmState.INIT;


   private enum OuttakeArmState {DROP, TRANSFER, INIT, PARK}

    public OuttakeSlidesState SlidesState = OuttakeSlidesState.TRANSFER;
    public enum OuttakeSlidesState {LOW_BUCKET, HIGH_BUCKET, TRANSFER, HOLD}
    public OuttakeDropState DropState = OuttakeDropState.HIGH_BUCKET;

    public enum OuttakeDropState {LOW_BUCKET, HIGH_BUCKET}








    private static final double GRIP_OPEN_POS = 0.48;
    private static final double GRIP_CLOSED_POS = 0.15;
   private static final double WRIST_TRANSFER_POS = 0.01;
   private static final double WRIST_INIT_POS = 0.3;
   private static final double WRIST_DROP_POS = 0.58;
    private static final double WRIST_AUTO_PARK_POS = 0.27;

    private static final double ARM_TRANSFER_POS = 0.40;
   private static final double ARM_DROP_POS = 0.54;
   private static final double ARM_INIT_POS = 0.04;
    private static final double ARM_AUTO_PARK_POS = 0.32;






    public String Ogrip = "outtake_grip";
   public String Owrist = "outtake_wrist";
   public String Oarm = "outtake_arm";

    public MotorEx outtakeSlideLeft, outtakeSlideRight;
    public MotorGroup outtakeMotor;



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
    public Command   preTransfer() {
        return new SequentialGroup(

                Outtake.INSTANCE.openClaw(),

                new Delay(TimeSpan.fromSec(.5)),
                Outtake.INSTANCE.wristTransfer(),
                Outtake.INSTANCE.armTransfer(),
                new Delay(TimeSpan.fromSec(.5)),

                outtakeSlidesTransfer()




        );
    }
    @NonNull
    @Override
    public Command getDefaultCommand() {
        return new HoldPosition(outtakeMotor, controller, this);
    }
    public Command resetEncoder() {
        return new ParallelGroup(
                new ResetEncoder(outtakeSlideLeft,  this),
                new ResetEncoder(outtakeSlideRight, this)
        );
    }





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
    // WRIST COMMANDS
    public Command wristInit() {
        WristState = OuttakeWristState.INIT;
        return new ServoToPosition(outtakeWristServo, WRIST_INIT_POS, this);
    }


    public Command wristTransfer() {
        WristState = OuttakeWristState.TRANSFER;
        return new ServoToPosition(outtakeWristServo, WRIST_TRANSFER_POS, this);
    }


    public Command wristDrop() {
        WristState = OuttakeWristState.DROP;
        return new ServoToPosition(outtakeWristServo, WRIST_DROP_POS, this);
    }
    public Command wristPark() {
        WristState = OuttakeWristState.PARK;
        return new ServoToPosition(outtakeWristServo, WRIST_AUTO_PARK_POS, this);
    }


    // ARM COMMANDS
    public Command armInit() {
        ArmState = OuttakeArmState.INIT;
        return new ServoToPosition(outtakeArmServo, ARM_INIT_POS, this);
    }


    public Command armTransfer() {
        ArmState = OuttakeArmState.TRANSFER;
        return new ServoToPosition(outtakeArmServo, ARM_TRANSFER_POS, this);
    }


    public Command armDrop() {
        ArmState = OuttakeArmState.DROP;
        return new ServoToPosition(outtakeArmServo, ARM_DROP_POS, this);
    }
    public Command armPark() {
        ArmState = OuttakeArmState.PARK;
        return new ServoToPosition(outtakeArmServo, ARM_AUTO_PARK_POS, this);
    }


    // SLIDES COMMANDS



    // TRANSFER COMMANDS





    public Command Transfer() {
        return new SequentialGroup(

         new ParallelGroup(
                Claw.INSTANCE.swivelCentered(),
                Claw.INSTANCE.intakeArmTransfer(),
                Claw.INSTANCE.wristTransfer(),
                IntakeSlide.INSTANCE.transferMinRetracted(),
                new Delay(TimeSpan.fromMs(100)),
                armTransfer(),
                openClaw(),
                 outtakeSlidesTransfer(),
                 new Delay(TimeSpan.fromMs(300)),

                 wristTransfer()
        ),  new WaitUntil(() -> IntakeSlide.INSTANCE.extensionState
                == IntakeSlide.ExtensionState.RETRACTED ),
                new Delay(TimeSpan.fromMs(150)),


                closeClaw(),
                new Delay(TimeSpan.fromMs(500)),

                Claw.INSTANCE.openClaw(),
                Claw.INSTANCE.wristPostTransfer(),
                Claw.INSTANCE.intakeArmPostTransfer(),
                armDrop(),
                wristDrop(),
                autoMoveSlide()



        );
    }


    // Bucket Drop
    public Command autoMoveSlide() {
        if (DropState == OuttakeDropState.LOW_BUCKET) {
            return dropLowBucket();
        } else {
            return dropHighBucket();
        }
    }
    public Command preDrop() {
        return new ParallelGroup(
                armDrop(),
                wristDrop()
        );
    }
    public Command dropLowBucket() {
        DropState = OuttakeDropState.LOW_BUCKET;

        return new ParallelGroup(
                preDrop(),

                lowBucket()
        );


    }
    public Command dropHighBucket() {
        DropState = OuttakeDropState.HIGH_BUCKET;
        return new ParallelGroup(
                preDrop(),
                highBucket()

        );


    }
// Auto Commands
    public Command AutoPark() {
        return new SequentialGroup(
                armPark(),
                wristPark()
        );
    }



    @Override
    public void initialize() {
        outtakeArmServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Oarm);
        outtakeWristServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Owrist);
        outtakeGripServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Ogrip);
        outtakeMotor = new MotorGroup(new MotorEx(OslideLeft).reverse(), new MotorEx(OslideRight));












    }
}

