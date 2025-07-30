/*package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;

import org.firstinspires.ftc.teamcode.Subsystems.OuttakeSlides;

@TeleOp(name="OuttakeSlides PIDF Tuner")
public class OuttakeSlidesPidTuner extends PedroOpMode {
    private final OuttakeSlides slides = OuttakeSlides.INSTANCE;
    // how many encoder ticks to move for each test
    private static final int TEST_TICKS = 1350;
    private long startTime;
    private boolean running = false;

    @Override
    public void onInit() {
        super.onInit();
        // zero both encoders on init
        slides.resetEncoder().invoke();
        // you can tweak these gains before running
        // in your onInit() of the tuner OpMode
        OuttakeSlides.INSTANCE.resetEncoder().invoke();
        OuttakeSlides.INSTANCE.controller.setKP(0.005);
        OuttakeSlides.INSTANCE.controller.setKI(0);
        OuttakeSlides.INSTANCE.controller.setKD(0);

    }

    @Override
    public void onStartButtonPressed() {
        // press A to trigger one step test
        gamepadManager.getGamepad1().getDpadUp().setPressedCommand(() -> {
                    slides.resetEncoder().invoke();
                    new Delay(TimeSpan.fromSec(0.2)).invoke();
                    new HoldPosition(slides.outtakeMotor, slides.controller, slides).invoke();

                    startTime = System.currentTimeMillis();
                    running   = true;


                    return new SequentialGroup(
                            slides.highBucket(),
                            slides.outtakeSlidesTransfer()
                    );
                });


    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (running) {
            double pos = slides.outtakeSlideLeft.getCurrentPosition();
            long t   = System.currentTimeMillis() - startTime;
            telemetry.addData("t (ms)", t);
            telemetry.addData("ticks", pos);
            telemetry.update();

            // when the move finishes, stop logging
            if (slides.outtakeSlidesTransfer().isDone()) {
                running = false;
            }
        }
    }
}
*/