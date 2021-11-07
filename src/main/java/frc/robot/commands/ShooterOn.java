package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterOn extends CommandBase {

    Shooter shooter;

    public ShooterOn(Shooter shooter){
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void execute(){
        shooter.setSpeed(0.165);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
    
}