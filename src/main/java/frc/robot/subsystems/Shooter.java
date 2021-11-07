package frc.robot.subsystems;

import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax.*;

public class Shooter extends SubsystemBase{

    private final CANSparkMax shooter1 = new CANSparkMax(Constants.shooter1, MotorType.kBrushless);
    private final CANSparkMax shooter2 = new CANSparkMax(Constants.shooter2, MotorType.kBrushless);


    NetworkTableEntry shooter1Temp;
    NetworkTableEntry shooter2Temp;

    double killSwitch = 1;

    boolean flip = false;

    public Shooter(){
        shooter1.setInverted(flip);
        shooter2.setInverted(!flip);

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("datatable");



        shooter1Temp = table.getEntry("Shooter 1 Temperature");
        shooter2Temp = table.getEntry("Shooter 2 Temperature");
    }

    
    @Override
    public void periodic(){
        shooter1Temp.setDouble(getShooter1Temp());
        shooter2Temp.setDouble(getShooter2Temp());

        if(getShooter1Temp() >= 80){
            killSwitch = 0;
            System.out.println("SHOOTER TOO HOT!!!!");
        }else{
            killSwitch = 1;
        }

    }
    

    public double getShooter1Temp(){
        return shooter1.getMotorTemperature();
    }

    public double getShooter2Temp(){
        return shooter2.getMotorTemperature();
    }


    public void setSpeed(double speed){
        shooter1.set(speed * killSwitch);
        shooter2.set(speed * killSwitch);

    }

    public void setSpeed(double lspeed, double rspeed){
        shooter1.set(lspeed * killSwitch);
        shooter2.set(rspeed * killSwitch);
    }


    
}
