package org.firstinspires.ftc.teamcode.TeleOp;

import android.provider.SyncStateContract;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
import com.rowanmcalpin.nextftc.ftc.gamepad.GamepadManager;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.tuners_tests.constants.FConstants;
import org.firstinspires.ftc.teamcode.tuners_tests.constants.LConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSlide;

@TeleOp(name = "Robot Centric")
public class PedroNextTeleOp extends PedroOpMode{
    private Follower follower;
    private final Pose startPose = new Pose(0, 0, 0);

    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);


    }

    @Override
    public void onWaitForStart() {

    }

    @Override
    public void onStartButtonPressed() {
        // Start robot-centric teleop drive
        follower.startTeleopDrive();

        // Bind subsystem commands
// GP1
        gamepadManager.getGamepad1().getCircle().setPressedCommand(Claw.INSTANCE::toggleClaw);
        gamepadManager.getGamepad1().getLeftBumper().setPressedCommand(IntakeSlide.INSTANCE::toggleIntakeSlides);
        gamepadManager.getGamepad1().getRightStick().getButton().setPressedCommand(Claw.INSTANCE::toggleSwivel);
        gamepadManager.getGamepad1().getRightBumper().setPressedCommand(Claw.INSTANCE::pick);
// GP2
        gamepadManager.getGamepad2().getRightStick().getButton().setPressedCommand(Outtake.INSTANCE::Transfer);
        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(Outtake.INSTANCE::highBucket);
        gamepadManager.getGamepad2().getDpadLeft().setPressedCommand(Outtake.INSTANCE::dropLowBucket);
        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(Outtake.INSTANCE::outtakeSlidesTransfer);
        gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(Outtake.INSTANCE::toggleClaw);
        gamepadManager.getGamepad2().getTriangle().setPressedCommand(Outtake.INSTANCE::preDrop);
        gamepadManager.getGamepad2().getCircle().setPressedCommand(Outtake.INSTANCE::armTransfer);




    }

    @Override
    public void onUpdate() {
        // Drive update from follower
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, false);
        follower.update();

        /* Telemetry Outputs of our Follower */
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));

        /* Update Telemetry to the Driver Hub */
       telemetry.update();
    }

    @Override
    public void onStop() {
        // no-op
    }
}