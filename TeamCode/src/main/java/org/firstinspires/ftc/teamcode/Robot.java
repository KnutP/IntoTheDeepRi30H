package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Robot {

    private DcMotor lfDrive, lbDrive, rfDrive, rbDrive, lift, lIntake, rIntake;
    private Servo clamp, pivot, kicker, capstone;

    private HardwareMap hwMap;

    OpMode opMode;


    public void init(HardwareMap ahwMap, OpMode op) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        opMode = op;

        this.clamp = hwMap.get(Servo.class, "clamp");
        this.pivot = hwMap.get(Servo.class, "pivot");
        this.kicker = hwMap.get(Servo.class, "kicker");
        this.capstone = hwMap.get(Servo.class, "capstone");

        // Define and Initialize Motors
        this.lift = hwMap.get(DcMotor.class, "lift");
        this.lIntake = hwMap.get(DcMotor.class, "lIntake");
        this.rIntake = hwMap.get(DcMotor.class, "rIntake");

        this.lfDrive  = hwMap.get(DcMotor.class, "lfDrive");
        this.lbDrive  = hwMap.get(DcMotor.class, "lbDrive");
        this.rfDrive = hwMap.get(DcMotor.class, "rfDrive");
        this.rbDrive = hwMap.get(DcMotor.class, "rbDrive");
        lfDrive.setDirection(DcMotor.Direction.REVERSE);
        lbDrive.setDirection((DcMotor.Direction.REVERSE));
        rfDrive.setDirection(DcMotor.Direction.FORWARD);
        rbDrive.setDirection((DcMotor.Direction.FORWARD));

        stop();

        setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lfDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lbDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rfDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rbDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void setIntakePower(double intakePower){
        lIntake.setPower(intakePower);
        rIntake.setPower(-intakePower);
    }

    public void setLiftPower(double liftPower){
        lift.setPower(liftPower);
    }

    public void setPivotPosition(double pivotPosition){
        pivot.setPosition(pivotPosition);
    }

    public void setClampPosition(double clampPosition){
        clamp.setPosition(clampPosition);
    }

    public void setKickerPosition(double kickerPosition){
        kicker.setPosition(kickerPosition);
    }

    public void setCapstonePosition(double capstonePosition) {
        capstone.setPosition(capstonePosition);
    }

    public void drive(double lPower, double rPower){
        lfDrive.setPower(lPower);
        lbDrive.setPower(lPower);
        rfDrive.setPower(rPower);
        rbDrive.setPower(rPower);
    }

    public void stop(){
        lfDrive.setPower(0);
        lbDrive.setPower(0);
        rfDrive.setPower(0);
        rbDrive.setPower(0);
    }

    public void setMotorMode(DcMotor.RunMode mode) {
        rfDrive.setMode(mode);
        rbDrive.setMode(mode);
        lfDrive.setMode(mode);
        lbDrive.setMode(mode);
    }
}
