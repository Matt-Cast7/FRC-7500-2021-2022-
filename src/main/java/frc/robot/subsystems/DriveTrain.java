package frc.robot.subsystems;

import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.networktables.NetworkTable;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

import java.util.function.DoubleSupplier;

import javax.swing.plaf.TreeUI;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax.*;

public class DriveTrain extends SubsystemBase{
    
    private final CANSparkMax leftMaster = new CANSparkMax(Constants.Left1, MotorType.kBrushless);
    private final CANSparkMax leftSlave = new CANSparkMax(Constants.Left2, MotorType.kBrushless);
    private final CANSparkMax rightMaster = new CANSparkMax(Constants.Right1, MotorType.kBrushless);
    private final CANSparkMax rightSlave = new CANSparkMax(Constants.Right2, MotorType.kBrushless);

    Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);



    
    

    static double leftEncoderZero = 0;
    static double rightEncoderZero = 0;

    private static double m_ramp;
    private static double sensitivity;

    static double LeftEncoderZero = 0;
    static double RightEncoderZero = 0;

    CANEncoder m_leftEncoder = leftMaster.getEncoder();
    CANEncoder m_rightEncoder = rightMaster.getEncoder();

    NetworkTableEntry left1;
    NetworkTableEntry left2;
    NetworkTableEntry right1;
    NetworkTableEntry right2;

    NetworkTableEntry gyroEntry;


    public DriveTrain(){

        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);

        leftMaster.setInverted(false);
        rightMaster.setInverted(true);

        resetEncoders();

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("datatable");
        gyroEntry = table.getEntry("Gyro");


        left1 = table.getEntry("Left 1 Temp");
        left2 = table.getEntry("Left 2 Temp");
        right1 = table.getEntry("Right 1 Temp");
        right2 = table.getEntry("Right 2 Temp");


    }

    @Override
    public void periodic(){
        
        leftMaster.setIdleMode(IdleMode.kCoast);
        rightMaster.setIdleMode(IdleMode.kCoast);
        

        left1.setDouble(leftMaster.getMotorTemperature());
        left2.setDouble(leftSlave.getMotorTemperature());
        right1.setDouble(rightMaster.getMotorTemperature());
        right2.setDouble(rightSlave.getMotorTemperature());

        gyroEntry.setDouble(getHeading().getDegrees());

        
    }
 
    public void setRamp(DoubleSupplier throttle){
        m_ramp = (throttle.getAsDouble());
    }

    public void setSpeed(double left, double right, boolean a){
        
        leftMaster.set(left * 0.5);
        rightMaster.set(right * 0.5);
    }

    public void setSpeed(double left, double right){
        
        if(left <= Constants.deadzone && left >= -Constants.deadzone){
            leftMaster.setOpenLoopRampRate(0);
            leftMaster.set(0);
        }else{
            leftMaster.setOpenLoopRampRate(m_ramp);
            leftMaster.set(left);
        }
        
        if(right <= Constants.deadzone && right >= -Constants.deadzone){
            rightMaster.setOpenLoopRampRate(0);
            rightMaster.set(0);
        }else{
            rightMaster.setOpenLoopRampRate(m_ramp);
            rightMaster.set(right);
        }
        
    }

    public void setSpeed(double speed){
        leftMaster.setOpenLoopRampRate(3);
        rightMaster.setOpenLoopRampRate(3);

        leftMaster.set(speed * 1.04);
        rightMaster.set(speed);
    }



    public void goDistance(double distance, double power){


        resetEncoders();

        double heading = getHeading().getDegrees();


        
        if(distance < 0){
            
            do{

                if(getHeading().getDegrees() < heading - 2){
                    setSpeed(-power , -power* 1.03, true);                
                }

                if(getHeading().getDegrees() > heading + 2){
                    setSpeed(-power* 1.03, -power , true);
                }

                if(getHeading().getDegrees() > heading - 2 && getHeading().getDegrees() < heading + 2){
                    setSpeed(-power);
                }


               }while(getWheelAverage() > Units.inchesToMeters(distance));
      
        }else if(distance > 0){

            do{

                if(getHeading().getDegrees() < heading - 2){
                    setSpeed(power * 1.03, power, true);                
                }

                if(getHeading().getDegrees() > heading + 2){
                    setSpeed(power, power * 1.03, true);
                }

                if(getHeading().getDegrees() > heading - 2 && getHeading().getDegrees() < heading + 2){
                    setSpeed(power);
                }
    
          
              }while(getWheelAverage() < Units.inchesToMeters(distance));
          
        }

        setSpeed(0, 0);

        
    
    }


    public double getLeftDistanceTraveled(){
        return (getLeftEncoderPos() * (Units.inchesToMeters(Constants.wheelRadius) * 2 * Math.PI));
    }

    public double getRightDistanceTraveled(){
        return (getRightEncoderPos() *(Units.inchesToMeters(Constants.wheelRadius) * 2 * Math.PI));
    }

    public double getLeftEncoderPos(){
        return (m_leftEncoder.getPosition() - leftEncoderZero) / Constants.gearRatio;
    }

    public double getRightEncoderPos(){
        return (m_rightEncoder.getPosition() - rightEncoderZero) / Constants.gearRatio;
    }

    public double getWheelAverage(){
        return (getLeftDistanceTraveled() + getRightDistanceTraveled())/2;
    }

    public void resetEncoders(){
        leftEncoderZero = m_leftEncoder.getPosition();
        rightEncoderZero = m_rightEncoder.getPosition();
    }

    public Rotation2d getHeading(){
        return Rotation2d.fromDegrees(-gyro.getAngle());
    }

    public void resetGyro(){
        gyro.reset();
    }


}
