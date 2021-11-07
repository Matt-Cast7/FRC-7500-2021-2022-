package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterOff extends CommandBase{

    Shooter shooter;

    public ShooterOff(Shooter shooter){
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void execute(){
        shooter.setSpeed(0);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
    
}
