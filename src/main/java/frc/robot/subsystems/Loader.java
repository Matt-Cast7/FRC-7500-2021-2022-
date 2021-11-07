package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Loader extends SubsystemBase{

    Servo loader = new Servo(0);

    public Loader(){

    }

    public void setPos(int pos){
        //loader.set(pos);
        loader.setAngle(pos);
    }
    
}
