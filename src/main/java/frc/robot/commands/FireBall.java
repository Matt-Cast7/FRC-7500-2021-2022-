package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Loader;

public class FireBall extends CommandBase{

    Loader m_loader;
    
    public FireBall(Loader m_loader){
        this.m_loader = m_loader;
        addRequirements(m_loader);
    }

    @Override
    public void execute(){
        m_loader.setPos(50);
        
    }

    @Override
    public boolean isFinished(){
        return true;
    }
    
}
