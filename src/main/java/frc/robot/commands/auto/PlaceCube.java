package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.BotSubsystems;
import frc.robot.commands.forklift.ForkliftToPosition;
import frc.robot.commands.swervedrive.TimedSwerve;

public class PlaceCube extends SequentialCommandGroup{

    private final double DRIVE_FORWARD_TIME = 0.8;
    private final double DRIVE_BACKWARD_TIME = 0.6;

    public PlaceCube(){

        addCommands(

            //Lift the forklift before moving forward
            new ForkliftToPosition(BotSubsystems.forklift, AutoConstants.Levels.HIGH),

            //Move forward to be over the pole
            new TimedSwerve(BotSubsystems.swerveDriver, AutoConstants.DEFAULT_DRIVE_SPEED, 0, 0, DRIVE_FORWARD_TIME,0.1),

            //Open the clamp
            BotSubsystems.forklift.openClampCommand(),

            new TimedSwerve(BotSubsystems.swerveDriver, -AutoConstants.DEFAULT_DRIVE_SPEED, 0, 0, DRIVE_BACKWARD_TIME,0.1),

            new ForkliftToPosition(BotSubsystems.forklift, AutoConstants.Levels.PICKUP)
        );
    }
}
