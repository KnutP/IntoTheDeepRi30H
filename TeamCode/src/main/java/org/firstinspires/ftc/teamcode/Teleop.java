package org.firstinspires.ftc.teamcode;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Ri30HTeleop", group = "Iterative Opmode")
//@Disabled
public class Teleop extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Robot robot = new Robot();

    double xVelocity;
    double yVelocity;
    double wVelocity;
    boolean isIntakeOut = false;
    boolean isExtend = false;
    enum HopperPosition {
        IN,
        STAGE,
        DUMP
    }
    HopperPosition hopperPosition = HopperPosition.IN;

    @Override
    public void init() {
        robot.init(hardwareMap, this);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        // ***** DRIVER CODE *****

        // Drivetrain
        yVelocity = gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y);
        xVelocity = -gamepad1.left_stick_x*Math.abs(gamepad1.left_stick_x);
        wVelocity = gamepad1.right_stick_x*Math.abs(gamepad1.right_stick_x)/4;
        yVelocity = Range.clip(yVelocity, -1.0, 1.0);
        xVelocity = Range.clip(xVelocity, -1.0, 1.0);
        wVelocity = Range.clip(wVelocity, -1.0, 1.0);

        robot.mechanumDrive(xVelocity, yVelocity, wVelocity);


        // Intake
        if (gamepad1.left_bumper) { // in
            robot.setIntakePower(1);
        } else if (gamepad1.left_trigger > 0.2) { // out
            robot.setIntakePower(-gamepad1.left_trigger);
        } else {
            robot.setIntakePower(gamepad2.left_stick_y);
        }

        // Intake Servo
        if (gamepad1.x || gamepad2.right_bumper) { // in
            isIntakeOut = false;
        } else if (gamepad1.b || gamepad2.right_trigger > 0.2) { // out
            isIntakeOut = true;
        }

        if (isIntakeOut) { // out
            robot.setIntakePosition(0);
        } else { // in
            robot.setIntakePosition(1);
        }

        // Hopper position
        if (gamepad1.dpad_up || gamepad2.dpad_up) {
            hopperPosition = HopperPosition.DUMP;
        }
        else if (gamepad1.dpad_down || gamepad2.dpad_down) {
            hopperPosition = HopperPosition.IN;
        }

        if (hopperPosition == HopperPosition.IN) {
            robot.setHopperPosition(1);
        } else if (hopperPosition == HopperPosition.DUMP) {
            robot.setHopperPosition(0);
        }

        // ASCEND
        if (gamepad1.dpad_left || gamepad2.dpad_left) {
            robot.setAscendPower(1);
        } else if (gamepad1.dpad_right || gamepad2.dpad_right) {
            robot.setAscendPower(-1);
        } else {
            robot.setAscendPower(0);
        }


        // Extend position
        if(gamepad1.y || gamepad2.y) {
            isExtend = true;
        } else if(gamepad1.a || gamepad2.a) {
            isExtend = false;
        }

        if(isExtend) {
            robot.setExtendPosition(1);
        } else {
            robot.setExtendPosition(0);
        }


        // Lift
        if (gamepad1.right_bumper) {
            robot.setLiftPower(1);
        } else if (gamepad1.right_trigger > 0.2) {
            robot.setLiftPower(-gamepad1.right_trigger);
        }
        else if (Math.abs(gamepad2.right_stick_y) > 0.2) {
            robot.setLiftPower(gamepad2.right_stick_y);
        } else {
            robot.setLiftPower(0);
        }


    }

    @Override
    public void stop() {
    }

}