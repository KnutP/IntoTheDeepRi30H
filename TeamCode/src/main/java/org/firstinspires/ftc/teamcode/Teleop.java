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

    double xVelocity;
    double yVelocity;
    double wVelocity;
    double armPower;
    double gripperDown = 1;
    double gripperUp = 0.65;
    boolean isGripping = false;
    boolean isFiring = false;
    boolean isPlaneUp = false;

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
        yVelocity = gamepad1.left_stick_y*Math.abs(gamepad1.left_stick_y);
        xVelocity = -gamepad1.left_stick_x*Math.abs(gamepad1.left_stick_x);
        wVelocity = gamepad1.right_stick_x*Math.abs(gamepad1.right_stick_x)/4;
        yVelocity = Range.clip(yVelocity, -1.0, 1.0);
        xVelocity = Range.clip(xVelocity, -1.0, 1.0);
        wVelocity = Range.clip(wVelocity, -1.0, 1.0);

        robot.mechanumDrive(xVelocity, yVelocity, wVelocity);

        // Arm
        if(gamepad1.left_bumper || gamepad2.dpad_up) {
            armPower = 0.7; // up
        } else if (gamepad1.left_trigger > 0.5 || gamepad2.dpad_down) {
            armPower = -0.7; // down
        } else {
            armPower = 0;
        }

        robot.setArmPower(armPower);


//         Scoop
        if(gamepad1.right_bumper || gamepad2.right_bumper) {
            isGripping = false;
        } else if (gamepad1.right_trigger > 0.5 || gamepad2.right_trigger > 0.5) {
            isGripping = true;
        }

        if(isGripping) {
            robot.setGripperPosition(gripperDown);
        } else {
            robot.setGripperPosition(gripperUp);
        }


        if(gamepad1.dpad_up) {
            isPlaneUp = true;
        } else if (gamepad1.dpad_down) {
            isPlaneUp = false;
        }

        if(isPlaneUp) {
            robot.setPitchPosition(0);
        } else {
            robot.setPitchPosition(1);
        }

        if(gamepad1.y) {
            isFiring = true;
        } else if (gamepad1.a) {
            isFiring = false;
        }

        if(isFiring){
            robot.setFirePosition(1);
        } else {
            robot.setFirePosition(0);
        }


    }

    @Override
    public void stop() {
    }

}