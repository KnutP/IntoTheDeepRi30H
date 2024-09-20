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
    boolean isShooting = false;
    boolean isGrabbing = false;

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

        // ***** DRIVER CODE *****

        // Drivetrain
        yVelocity = gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y);
        xVelocity = -gamepad1.left_stick_x*Math.abs(gamepad1.left_stick_x);
        wVelocity = gamepad1.right_stick_x*Math.abs(gamepad1.right_stick_x)/4;
        yVelocity = Range.clip(yVelocity, -1.0, 1.0);
        xVelocity = Range.clip(xVelocity, -1.0, 1.0);
        wVelocity = Range.clip(wVelocity, -1.0, 1.0);

        robot.mechanumDrive(xVelocity, yVelocity, wVelocity);

        // Shooter
        if(gamepad1.y) {
            isShooting = true;
        } else if(gamepad1.a) {
            isShooting = false;
        }

        // Intake
        if(gamepad1.dpad_up) {
            robot.setIntakePower(-1);
        } else if (gamepad1.dpad_down) {
            robot.setIntakePower(1);
        } else {
            robot.setIntakePower(gamepad2.left_stick_y);
        }



        // ***** OPERATOR CODE *****

        // Shooter
        if(gamepad2.y) {
            isShooting = true;
        } else if(gamepad2.a) {
            isShooting = false;
        }

        if(isShooting) {
            robot.setShooterPower(1);
        } else {
            robot.setShooterPower(0);
        }

        // Arm
        robot.setArmPower(gamepad2.right_stick_y);

        if(gamepad2.right_bumper) {
            isGrabbing = true;
        } else if (gamepad2.right_trigger > 0.5) {
            isGrabbing = false;
        }

        if(isGrabbing) {
            robot.setGrabberPosition(1);
        } else {
            robot.setGrabberPosition(0);
        }

    }

    @Override
    public void stop() {
    }

}