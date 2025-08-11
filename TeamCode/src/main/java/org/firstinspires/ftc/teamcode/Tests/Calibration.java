package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;


import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSlide;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

@TeleOp(name = "Calibration")
public class Calibration extends PedroOpMode {

    public Calibration() {
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
        gamepadManager.getGamepad1().getDpadRight().setPressedCommand(Claw.INSTANCE::ClawServoUp);
        gamepadManager.getGamepad1().getDpadLeft().setPressedCommand(Claw.INSTANCE::ClawServoDown);

        /**/
// GP2
       /* gamepadManager.getGamepad2().getRightStick().getButton().setPressedCommand(Outtake.INSTANCE::Transfer);
        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(Outtake.INSTANCE::highBucket);
        gamepadManager.getGamepad2().getDpadLeft().setPressedCommand(Outtake.INSTANCE::dropLowBucket);
        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(Outtake.INSTANCE::outtakeSlidesTransfer);
        gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(Outtake.INSTANCE::toggleClaw);
        gamepadManager.getGamepad2().getTriangle().setPressedCommand(Outtake.INSTANCE::preDrop);
        gamepadManager.getGamepad2().getCircle().setPressedCommand(Outtake.INSTANCE::armTransfer); */






        telemetry.addLine("Intake Grip: GP1 + Dpad_right, - Dpad_left");
        telemetry.addData("    Grip Position", Claw.INSTANCE.intakeGripServo.getPosition());
        telemetry.addLine("----------");

        telemetry.update();

    }



}
