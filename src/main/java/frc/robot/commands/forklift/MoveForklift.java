
package frc.robot.commands.forklift;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.BotControllers;
import frc.robot.consoles.Logger;
import frc.robot.subsystems.Forklift;

import frc.robot.consoles.Shuffler;
import edu.wpi.first.wpilibj.shuffleboard.*;
import java.util.Map;
import edu.wpi.first.networktables.GenericEntry;

public class MoveForklift extends CommandBase {

    private Forklift m_forklift;

    private ShuffleboardLayout motorSpeedsLayout = Shuffler.constructLayout(Shuffler.m_forkliftTab, "Motor Speeds", 4, 0, 6, 3, 1, 2, "LEFT");
    private ShuffleboardLayout motorEncodersLayout = Shuffler.constructLayout(Shuffler.m_forkliftTab, "Motor Encoders", 0, 3, 4, 2, 1, 2, "LEFT");

    private GenericEntry extenderSpeedEntry = motorSpeedsLayout
        .add("Extender Speed", 1.0)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 1.0))
        .getEntry();
    private GenericEntry elevatorSpeedEntry = motorSpeedsLayout
        .add("Elevator Speed", 1.0)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 1.0))
        .getEntry();
    
    private GenericEntry extenderEncoderEntry = motorEncodersLayout
        .add("Extender Encoder", 0)
        .getEntry();
    private GenericEntry elevatorEncoderEntry = motorEncodersLayout
        .add("Elevator Encoder", 0)
        .getEntry();
    

    public MoveForklift(Forklift forklift) {
        Logger.setup("Constructing Command: MoveForklift...");

        // Add given subsystem requirements
        m_forklift = forklift;
        addRequirements(m_forklift);
    }

    @Override
    public void initialize() {
        Logger.action("Initializing Command: MoveForklift...");
    }

    @Override
    public void execute() {
        double extenderPower = BotControllers.xbox2.xbox.getRightY();
        double elevatorPower = BotControllers.xbox2.xbox.getLeftY();

        m_forklift.move("Extender", extenderPower,0.16);
        m_forklift.move("Elevator", elevatorPower, 0.16);

        double clawPower = BotControllers.xbox2.xbox.getRightTriggerAxis() - (BotControllers.xbox2.xbox.getLeftTriggerAxis()/2);
        m_forklift.move("ClawLift", clawPower);

        Logger.info(" Extender Power: " + extenderPower);

        //Retrieve Shuffleboard data from subsystem
        extenderEncoderEntry.setDouble(m_forklift.getEncoderPosition("Extender"));
        elevatorEncoderEntry.setDouble(m_forklift.getEncoderPosition("Elevator"));

    }



    // This command continues until interrupted
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            Logger.ending("Interrupting Command: MoveForklift...");
        } else {
            Logger.ending("Ending Command: MoveForklift...");
        }
        m_forklift.stopAllMotors();
    }

}