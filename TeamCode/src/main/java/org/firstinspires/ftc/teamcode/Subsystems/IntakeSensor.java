package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.units.Distance;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

import android.graphics.Color;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.LambdaCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.firstinspires.ftc.robotcontroller.external.samples.SensorREV2mDistance;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


import android.graphics.Color;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.LambdaCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class IntakeSensor extends Subsystem {
    public static final IntakeSensor INSTANCE = new IntakeSensor();

    public SensorColor colorSensor;
    public DistanceSensor distanceSensor;


    public String intakeColorSensor = "intake_sensor";
    public String intakeDistanceSensor = "intake_sensor";

    public enum SensedState { SAMPLE, NOSAMPLE }
    public IntakeSensor.SensedState sensedState = IntakeSensor.SensedState.NOSAMPLE;




    Distance distanceThreshold = Distance.fromCm(5);

    public Command checkSample() {
        return new LambdaCommand() {
            public void execute() {
                if (distanceSensor.getDistance(DistanceUnit.CM) < distanceThreshold.getValue()) {
                    sensedState = SensedState.SAMPLE;
                } else {
                    sensedState = SensedState.NOSAMPLE;
                }
            }
        };
    }
    public Command autoTransfer(){


        if (sensedState == SensedState.SAMPLE){
           return Outtake.INSTANCE.Transfer();

        } else {
            return checkSample();
        }

    }


    @Override
    public void initialize() {
        distanceSensor = OpModeData.INSTANCE.getHardwareMap().get(DistanceSensor.class, intakeDistanceSensor);



    }

}


