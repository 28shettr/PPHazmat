package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;

public class Claw extends Subsystem {
    // BOILERPLATE
    public static final Claw INSTANCE = new Claw();
    public Servo intakeGripServo, intakeSwivelServo, intakeWristServo, intakeArmServo;


    // State enums for Claw and Swivel
    private enum IntakeGripState { OPEN, CLOSED }
    private IntakeGripState GripState = IntakeGripState.CLOSED;

    private enum SwivelState { CENTERED, ROTATED }
    private SwivelState swivelState = SwivelState.CENTERED;

    private Claw() { }

    // Servo position constants
    private static final double GRIP_OPEN_POS     = 0.57;
    private static final double GRIP_CLOSED_POS   = 0.23;
    private static final double SWIVEL_180_POS    = 0.225;
    private static final double SWIVEL_CENTER_POS = 0.495;
    private static final double WRIST_PICKUP_POS  = 0.88;
    private static final double WRIST_POST_POS    = 0.56;
    private static final double WRIST_TRANSFER_POS    = 0.21;
    private static final double WRIST_postTRANSFER_POS    = 0.38;
    private static final double ARM_PREPOST_POS   = 0.40;
    private static final double ARM_PICKUP_POS    = 0.27;
    private static final double ARM_TRANSFER    = 0.59;
    private static final double ARM_postTRANSFER    = 0.52;




    // USER CODE

    public String grip = "intake_grip";
    public String swivel = "intake_swivel";
    public String wrist = "intake_wrist";
    public String arm = "intake_arm";

    // Grip Commands
    public Command openClaw() {
        GripState = IntakeGripState.OPEN;
        return new ServoToPosition(intakeGripServo, GRIP_OPEN_POS, this);
    }

    public Command closeClaw() {
        GripState = IntakeGripState.CLOSED;
        return new ServoToPosition(intakeGripServo, GRIP_CLOSED_POS, this);
    }

    public Command toggleClaw() {
        if (GripState == IntakeGripState.OPEN) {
            return closeClaw();
        } else {
            return openClaw();
        }
    }

    // Swivel Commands
    public Command swivel180() {
        swivelState = SwivelState.ROTATED;
        return new ServoToPosition(intakeSwivelServo, SWIVEL_180_POS, this);
    }

    public Command swivelCentered() {
        swivelState = SwivelState.CENTERED;
        return new ServoToPosition(intakeSwivelServo, SWIVEL_CENTER_POS, this);
    }

    public Command toggleSwivel() {
        if (swivelState == SwivelState.ROTATED) {
            return swivelCentered();
        } else {
            return swivel180();
        }
    }

    // Wrist Commands
    public Command wristPickup() {
        return new ServoToPosition(intakeWristServo, WRIST_PICKUP_POS, this);
    }

    public Command wristPostPickup() {
        return new ServoToPosition(intakeWristServo, WRIST_POST_POS, this);
    }
    public Command wristTransfer() {
        return new ServoToPosition(intakeWristServo, WRIST_TRANSFER_POS, this);
    }
    public Command wristPostTransfer() {
        return new ServoToPosition(intakeWristServo, WRIST_postTRANSFER_POS, this);
    }

    // Arm Commands
    public Command intakeArmPre_PostPickup() {
        return new ServoToPosition(intakeArmServo, ARM_PREPOST_POS, this);
    }
    public Command intakeArmTransfer() {
        return new ServoToPosition(intakeArmServo, ARM_TRANSFER, this);
    }
    public Command intakeArmPostTransfer() {
        return new ServoToPosition(intakeArmServo, ARM_postTRANSFER, this);
    }

    public Command intakeArmPickup() {
        return new ServoToPosition(intakeArmServo, ARM_PICKUP_POS, this);
    }
    public Command prePick() {
        return new ParallelGroup(
                Claw.INSTANCE.intakeArmPickup(),
                Claw.INSTANCE.wristPickup(),
                Claw.INSTANCE.openClaw()
        );
    }

    public Command pick() {
       return new SequentialGroup(

         new ParallelGroup(
                Claw.INSTANCE.intakeArmPre_PostPickup(),
                Claw.INSTANCE.wristPickup(),
                Claw.INSTANCE.openClaw()
        ),

                new Delay(TimeSpan.fromMs(50)),
                Claw.INSTANCE.intakeArmPickup(),
                new Delay(TimeSpan.fromMs(150)),
                Claw.INSTANCE.closeClaw(),
                new Delay(TimeSpan.fromMs(67)),
                Claw.INSTANCE.intakeArmPre_PostPickup()


        );
    }
    @Override
    public void initialize() {
        intakeGripServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, grip);
        intakeSwivelServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, swivel);
        intakeWristServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, wrist);
        intakeArmServo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, arm);
    }
}
