package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;

public class ShootBall extends CommandBase{

    private final Shooter m_Shooter;
    private final DriveTrain m_DriveTrain;
    private final Loader m_Loader;

    public ShootBall(Shooter m_Shooter, DriveTrain m_DriveTrain, Loader m_Loader){
        this.m_Shooter = m_Shooter;
        this.m_DriveTrain = m_DriveTrain;
        this.m_Loader = m_Loader;
        addRequirements(m_Shooter, m_DriveTrain, m_Loader);
    }


    @Override
    public void execute(){
        m_Shooter.setSpeed(0.33 , 0.32);//33 32

        m_DriveTrain.goDistance(180, 0.3);
        m_Loader.setPos(130);

      Timer.delay(1.5);
      m_Loader.setPos(50);
      Timer.delay(5);
      m_Loader.setPos(130);
      m_Shooter.setSpeed(0);

     m_DriveTrain.goDistance(-180, 0.3);


        // if(toggle.getAsBoolean()){
        //     m_Shooter.setSpeed(lSpeed.getAsDouble(), rSpeed.getAsDouble());
        //     m_Shooter.setSpeed(0.5);
        // }else{
        //     m_Shooter.setSpeed(0);
            
        // }
    }

    @Override
    public void end(boolean isInterupted){
        m_Shooter.setSpeed(0);
    }

    @Override
    public boolean isFinished(){
        return true;
    }


    
}
