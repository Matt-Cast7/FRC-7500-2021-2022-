package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class TankDrive extends CommandBase{
    
    private final DriveTrain m_DriveTrain;
    private final DoubleSupplier left;
    private final DoubleSupplier right;
    private final DoubleSupplier ramp;
    private final DoubleSupplier sensitivity;
    

    public TankDrive(DriveTrain drive, DoubleSupplier left, DoubleSupplier right, DoubleSupplier rightThrottle, DoubleSupplier leftThrottle){
        m_DriveTrain = drive;
        this.left = left;
        this.right = right;
        ramp = rightThrottle;
        sensitivity = leftThrottle;
        addRequirements(drive);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_DriveTrain.setSpeed((right.getAsDouble()  * getSensitivtyMultiplier(sensitivity)), (left.getAsDouble() * getSensitivtyMultiplier(sensitivity)));
        m_DriveTrain.setRamp(ramp);
        //m_DriveTrain.setSpeed(left.getAsDouble(), right.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public double getSensitivtyMultiplier(DoubleSupplier sensitivity){
        return (((-sensitivity.getAsDouble()) + 1) / 2)*0.5;
    }

    @Override
    public void end(boolean isInterupted){
        m_DriveTrain.setSpeed(0);
        m_DriveTrain.setSpeed(0);
    }


}
