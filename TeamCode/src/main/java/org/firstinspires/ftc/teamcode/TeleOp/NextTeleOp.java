package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
import com.rowanmcalpin.nextftc.ftc.gamepad.Button;
import com.rowanmcalpin.nextftc.ftc.gamepad.GamepadManager;



import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSlide;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

@TeleOp(name = "NextFTC TeleOp Program Java")
public class NextTeleOp extends PedroOpMode {

    public NextTeleOp() {
        super(Claw.INSTANCE,IntakeSlide.INSTANCE,Outtake.INSTANCE);
    }
    // Change the motor names to suit your robot.
    public String frontLeftName = "leftFront_par0";
    public String frontRightName = "rightFront_par1";
    public String backLeftName = "leftBack";
    public String backRightName = "rightBack_perp";

    public MotorEx frontLeftMotor;
    public MotorEx frontRightMotor;
    public MotorEx backLeftMotor;
    public MotorEx backRightMotor;

    public MotorEx[] motors;

    public Command driverControlled;

    @Override
    public void onInit() {
        frontLeftMotor = new MotorEx(frontLeftName);
        backLeftMotor = new MotorEx(backLeftName);
        backRightMotor = new MotorEx(backRightName);
        frontRightMotor = new MotorEx(frontRightName);

        // Change the motor directions to suit your robot.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        motors = new MotorEx[] {frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor};
    }

    @Override
    public void onStartButtonPressed() {

        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1());
        driverControlled.invoke();


        // Bind subsystem commands
// GP1
        gamepadManager.getGamepad1().getCircle().setPressedCommand(Claw.INSTANCE::toggleClaw);
        gamepadManager.getGamepad1().getTriangle().setPressedCommand(IntakeSlide.INSTANCE::toggleIntakeSlides);
        gamepadManager.getGamepad1().getRightStick().getButton().setPressedCommand(Claw.INSTANCE::toggleSwivel);
        gamepadManager.getGamepad1().getRightBumper().setPressedCommand(Claw.INSTANCE::pick);
        gamepadManager.getGamepad1().getSquare().setPressedCommand(IntakeSlide.INSTANCE::transferMinRetracted);

        gamepadManager.getGamepad1().getLeftStick().getButton().setPressedCommand(Outtake.INSTANCE::Transfer);
        gamepadManager.getGamepad1().getDpadUp().setPressedCommand(Outtake.INSTANCE::highBucket);
        gamepadManager.getGamepad1().getDpadLeft().setPressedCommand(Outtake.INSTANCE::dropLowBucket);
        gamepadManager.getGamepad1().getLeftBumper().setPressedCommand(Outtake.INSTANCE::toggleClaw);
        gamepadManager.getGamepad1().getX().setPressedCommand(Outtake.INSTANCE::preDrop);



// GP2
       /* gamepadManager.getGamepad2().getRightStick().getButton().setPressedCommand(Outtake.INSTANCE::Transfer);
        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(Outtake.INSTANCE::highBucket);
        gamepadManager.getGamepad2().getDpadLeft().setPressedCommand(Outtake.INSTANCE::dropLowBucket);
        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(Outtake.INSTANCE::outtakeSlidesTransfer);
        gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(Outtake.INSTANCE::toggleClaw);
        gamepadManager.getGamepad2().getTriangle().setPressedCommand(Outtake.INSTANCE::preDrop);
        gamepadManager.getGamepad2().getCircle().setPressedCommand(Outtake.INSTANCE::armTransfer); */








    }



}
