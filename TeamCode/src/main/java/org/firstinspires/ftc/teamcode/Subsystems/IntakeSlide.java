package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToSeperatePositions;

import java.util.Map;

public class IntakeSlide extends Subsystem {
    // BOILERPLATE
    public static final IntakeSlide INSTANCE = new IntakeSlide();

    public Servo intakeSlideServoLeft, intakeSlideServoRight;

    // Extension state enum
    public enum ExtensionState { RETRACTED, IN_BETWEEN, EXTENDED }
    public ExtensionState extensionState = ExtensionState.RETRACTED;

    private IntakeSlide() { }

    // Servo position constants
    private static final double LEFT_RETRACTED_POS   = 0.21;
    private static final double RIGHT_RETRACTED_POS  = 0.225;
    private static final double LEFT_INBETWEEN_POS   = 0.35;
    private static final double RIGHT_INBETWEEN_POS  = 0.395;
    private static final double LEFT_EXTENDED_POS    = 0.575;
    private static final double RIGHT_EXTENDED_POS   = 0.62;

    // Hardware mapping names
    public String slideLeft  = "intake_slide_left";
    public String slideRight = "intake_slide_right";

    // Position commands
    public Command transferMinRetracted() {
        extensionState = ExtensionState.RETRACTED;
        return new MultipleServosToSeperatePositions(
                Map.of(
                        intakeSlideServoLeft, LEFT_RETRACTED_POS,
                        intakeSlideServoRight, RIGHT_RETRACTED_POS
                ),
                this
        );
    }

    public Command inBetween() {
        extensionState = ExtensionState.IN_BETWEEN;
        return new MultipleServosToSeperatePositions(
                Map.of(
                        intakeSlideServoLeft, LEFT_INBETWEEN_POS,
                        intakeSlideServoRight, RIGHT_INBETWEEN_POS
                ),
                this
        );
    }

    public Command maxExtension() {
        extensionState = ExtensionState.EXTENDED;
        return new MultipleServosToSeperatePositions(
                Map.of(
                        intakeSlideServoLeft, LEFT_EXTENDED_POS,
                        intakeSlideServoRight, RIGHT_EXTENDED_POS
                ),
                this
        );
    }

    public Command toggleIntakeSlides() {
        if (extensionState == ExtensionState.RETRACTED) {
            Claw.INSTANCE.prePick();
            return inBetween();

        } else   {
            Claw.INSTANCE.prePick();

            return maxExtension();

        }
    }
    public Command Init() {
        return new ParallelGroup(
                transferMinRetracted(),
                Claw.INSTANCE.openClaw(),
                Claw.INSTANCE.intakeArmTransfer(),
                Claw.INSTANCE.wristTransfer(),
                new Delay(TimeSpan.fromMs(167)),
                Outtake.INSTANCE.armInit(),
                Outtake.INSTANCE.wristInit(),
                Outtake.INSTANCE.closeClaw()



                );


    }

    @Override
    public void initialize() {
        intakeSlideServoLeft  = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, slideLeft);
        intakeSlideServoRight = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, slideRight);
    }
}
