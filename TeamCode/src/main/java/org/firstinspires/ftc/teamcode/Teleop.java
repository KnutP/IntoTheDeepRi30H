package org.firstinspires.ftc.teamcode;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Ri30HTeleop", group = "Iterative Opmode")
//@Disabled
public class Teleop extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Robot robot = new Robot();
    private enum ClampPosition {open, closed}
    private ClampPosition clampPosition = ClampPosition.open;
    private enum PivotPosition {in, out}
    private PivotPosition pivotPosition = PivotPosition.in;
    private enum KickerPosition {in, out}
    private KickerPosition kickerPosition = KickerPosition.in;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

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
        double lPower;
        double rPower;
        lPower = Math.abs(gamepad1.left_stick_y)*gamepad1.left_stick_y*0.6;
        rPower = Math.abs(-gamepad1.right_stick_y)*gamepad1.right_stick_y*0.6;
        robot.drive(lPower, rPower);


        // ***** OPERATOR CODE *****

        // Intake
        robot.setIntakePower(gamepad2.left_stick_y);

        // Lift
        robot.setLiftPower(0.5*gamepad2.right_stick_y);

        if(gamepad2.a){
            robot.setCapstonePosition(1);
        }
        if(gamepad2.b){
            robot.setCapstonePosition(0.4);
        }
        if(gamepad2.y){
            robot.setCapstonePosition(0.3);
        }

        // Pivot positions
        if(gamepad2.dpad_down){
            pivotPosition = PivotPosition.in;
        } else if(gamepad2.dpad_up){
            pivotPosition = PivotPosition.out;
        }
        switch(pivotPosition){
            case in:
                robot.setPivotPosition(1);
                break;
            case out:
                robot.setPivotPosition(0.17);
                break;
            default:
                robot.setPivotPosition(1);
                break;
        }


        // Clamp positions
        if(gamepad2.right_bumper){
            clampPosition = ClampPosition.open;
        } else if(gamepad2.right_trigger > 0.5){
            clampPosition = ClampPosition.closed;
        }
        switch(clampPosition){
            case open:
                robot.setClampPosition(0);
                break;
            case closed:
                robot.setClampPosition(0.7);
                break;
            default:
                robot.setClampPosition(0);
                break;
        }

    // Kicker positions
        if(gamepad2.left_bumper){
        kickerPosition = KickerPosition.out;
    } else if(gamepad2.left_trigger > 0.5){
        kickerPosition = KickerPosition.in;
    }
        switch(kickerPosition){
        case out:
            robot.setKickerPosition(0);
            break;
        case in:
            robot.setKickerPosition(0.4);
            break;
        default:
            robot.setClampPosition(0);
            break;
    }
}

    @Override
    public void stop() {
    }

}
