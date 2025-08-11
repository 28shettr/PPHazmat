package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake;
import org.firstinspires.ftc.teamcode.Subsystems.OuttakeSlides;

@TeleOp(name="OuttakeSlides PIDF Tuner")
public class OuttakeSlidesPidTuner extends PedroOpMode {
    // how many encoder ticks to move for each test
    private long startTime;
    private boolean running = false;

    private boolean repeat = false;

    double pos = Outtake.INSTANCE.outtakeSlideLeft.getCurrentPosition();

    @Override
    public void onInit() {
        super.onInit();
        // zero both encoders on init
        Outtake.INSTANCE.resetEncoder().invoke();
        // you can tweak these gains before running
        // in your onInit() of the tuner OpMode
        Outtake.INSTANCE.resetEncoder().invoke();
        Outtake.INSTANCE.controller.setKP(0.005);
        Outtake.INSTANCE.controller.setKI(0);
        Outtake.INSTANCE.controller.setKD(0);

    }

    @Override
    public void onStartButtonPressed() {
        // press A to trigger one step test
        gamepadManager.getGamepad1().getDpadUp().setPressedCommand(() -> {
                    Outtake.INSTANCE.resetEncoder();
                    new Delay(TimeSpan.fromSec(0.2));

                    startTime = System.currentTimeMillis();
                    running   = true;


            return new SequentialGroup(
                    Outtake.INSTANCE.highBucket(),

                    new Delay(TimeSpan.fromSec(0.5)),
                    Outtake.INSTANCE.outtakeSlidesTransfer()
            );

        });


    }

    @Override
    public void onUpdate() {

            double pos = Outtake.INSTANCE.outtakeSlideLeft.getCurrentPosition();
            long t   = System.currentTimeMillis() - startTime;
            telemetry.addData("t (ms):", t);
            telemetry.addData("ticks: ", pos);
            telemetry.update();



    }
}
