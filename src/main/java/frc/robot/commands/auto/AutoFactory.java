package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.BotCommands;
import frc.robot.BotSubsystems;
import frc.robot.commands.swervedrive.TimedSwerve;

/* This class factory will create a command group based on the starting location
    and target placement of the cube (eject on bottom or place on top shelf)
*/
public class AutoFactory extends SequentialCommandGroup {

    public AutoFactory(String location) {

        // STEP 1: Place the cube
        //addCommands(BotCommands.timedIntake);

        // STEP 2: Balance or move out of community
        
        switch (location){
            case "Center":
                // We're located in the center so balance on the charge station
                addCommands(new BalanceChargeStation(BotSubsystems.swerveDriver));
                break;
        
            //This is relative to the driver facing the field
            case "Left":
            case "Right":
                double sideToSideSpeed = (location == "Left") ? -AutoConstants.DEFAULT_DRIVE_SPEED : AutoConstants.DEFAULT_DRIVE_SPEED;
                addCommands(
                    new TimedSwerve(BotSubsystems.swerveDriver, 0, AutoConstants.DEFAULT_DRIVE_SPEED, 0, 0.4,0.1),
                    
                    new TurnDegrees(BotSubsystems.swerveDriver, -90),
                    
                    new TimedSwerve(BotSubsystems.swerveDriver, 0, AutoConstants.DEFAULT_DRIVE_SPEED, 0, 0.4,0.1),
                    
                    new WaitCommand(0.1),

                    BotSubsystems.forklift.openClampCommand(),

                    new TimedSwerve(BotSubsystems.swerveDriver, AutoConstants.FAST_DRIVE_SPEED,0 , 0, 2,0.1));
                break;
            
            default:
                throw new java.lang.Error("Unkown Location; should be {Left, Center, Right}");           
        }

    }

}