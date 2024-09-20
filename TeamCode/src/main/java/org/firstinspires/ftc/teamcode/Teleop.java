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
    private final ElapsedTime runtime = new ElapsedTime();
    private final Robot robot = new Robot();

    double leftPower;
    double rightPower;
    boolean isPivotOut = false;
    boolean isGrabbing = false;
    boolean isPivotReady = false;

    @Override
    public void init() {
        robot.init(hardwareMap, this);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        // Drivetrain
        leftPower = -gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y);
        rightPower = -gamepad1.right_stick_y*Math.abs(gamepad1.right_stick_y);
        leftPower = Range.clip(leftPower, -1.0, 1.0);
        rightPower = Range.clip(rightPower, -1.0, 1.0);

        robot.skidSteerDrive(leftPower, rightPower);

        // Lift
        if(gamepad1.y) {
            robot.setLiftPower(1);
        } else if(gamepad1.a) {
            robot.setLiftPower(-1);
        } else {
            robot.setLiftPower(-gamepad2.right_stick_y);
        }

        // Intake
        if(gamepad1.dpad_up) {
            robot.setIntakePower(-1);
        } else if (gamepad1.dpad_down) {
            robot.setIntakePower(1);
        } else {
            robot.setIntakePower(gamepad2.left_stick_y);
        }

        // Pivot
        if(gamepad1.left_bumper || gamepad2.dpad_up) {
            isPivotOut = true;
            isPivotReady = false;
        } else if (gamepad1.left_trigger > 0.5 || gamepad2.dpad_down) {
            isPivotOut = false;
            isPivotReady = false;
        } else if (gamepad2.dpad_left) {
            isPivotOut = false;
            isPivotReady = true;
        }

        if(isPivotOut) {
            robot.setPivotPosition(0);
        } else if(isPivotReady) {
            robot.setPivotPosition(0.4);
        } else {
            robot.setPivotPosition(1);
        }

        // Grabber
        if(gamepad1.right_bumper || gamepad2.right_bumper) {
            isGrabbing = true;
        } else if (gamepad1.right_trigger > 0.5 || gamepad2.right_trigger > 0.5) {
            isGrabbing = false;
        }

        if(isGrabbing) {
            robot.setGrabberPosition(0);
        } else {
            robot.setGrabberPosition(1);
        }

        // Duck spinner
        if(gamepad2.x) {
            robot.setDuckSpinnerPower(-1);
        } else if (gamepad2.y) {
            robot.setDuckSpinnerPower(-0.5);
        } else {
            robot.setDuckSpinnerPower(0);
        }

    }

    @Override
    public void stop() {
    }

}