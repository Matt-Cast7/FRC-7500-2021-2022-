package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.fasterxml.jackson.databind.ser.std.NumberSerializers.DoubleSerializer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;

public class XboxControl extends CommandBase{

    private DriveTrain m_DriveTrain;
    private Shooter m_Shooter;
    private Loader m_Loader;

    private DoubleSupplier leftSpeed;
    private DoubleSupplier rightSpeed;

    private BooleanSupplier trigger;
    private BooleanSupplier wheels;

    public boolean state = false;
    public boolean wheelState = false;

    public XboxControl(DriveTrain m_DriveTrain, Shooter m_Shooter, Loader m_Loader, DoubleSupplier leftSpeed, DoubleSupplier rightSpeed, BooleanSupplier trigger, BooleanSupplier wheels){
        this.m_DriveTrain = m_DriveTrain;
        this.m_Shooter = m_Shooter;
        this.m_Loader = m_Loader;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.trigger = trigger;
        this.wheels = wheels;

        addRequirements(m_DriveTrain);
    }

    @Override
    public void execute(){
        m_DriveTrain.setSpeed(leftSpeed.getAsDouble(), rightSpeed.getAsDouble(), true);
        if(trigger.getAsBoolean()){
            if(state){
                m_Loader.setPos(50);
                state = !state;
            }else{
                m_Loader.setPos(130);
                state = !state;
            }
        }

        if(wheels.getAsBoolean()){
            if(wheelState){
                m_Shooter.setSpeed(0.1);
                wheelState = !wheelState;
            }else{
                m_Shooter.setSpeed(0);
                wheelState = !wheelState;
            }
        }
    }
    
}
