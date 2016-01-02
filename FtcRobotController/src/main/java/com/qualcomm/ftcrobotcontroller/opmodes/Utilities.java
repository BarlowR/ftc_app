package com.qualcomm.ftcrobotcontroller.opmodes;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

/**
 * Created by Robert on 11/20/2015.
 */
public class Utilities {

    public static double[] controlArms(int speed, double ratio, double x1, double y1, double x2, double y2){
        double speedRearLeft;
        double speedRearRight;
        double speedFrontLeft;
        double speedFrontRight;
        double driveRearLeft;
        double driveRearRight;
        double driveFrontLeft;
        double driveFrontRight;


        double[] rear = joyToArms(x1,y1);
        speedRearLeft = speed*rear[0]/100.0;
        speedRearRight = speed*rear[1]/100.0;

        double[] front = joyToArms(x2,y2);
        speedFrontLeft = speed*front[0]/100.0;
        speedFrontRight = speed*front[1]/100.0;

        driveRearLeft = driveArmTrack(speedRearLeft, ratio);
        driveRearRight= driveArmTrack(speedRearRight, ratio);
        driveFrontLeft= driveArmTrack(speedFrontLeft, ratio);
        driveFrontRight= driveArmTrack(speedFrontRight, ratio);


        return new double[] {speedRearLeft, speedRearRight, speedFrontLeft, speedFrontRight, driveRearLeft, driveRearRight, driveFrontLeft, driveFrontRight};
    }

    public static double[] joyToArms(double x, double y){
        double armLeft = 0, armRight=0;

        armLeft = x/2 + y/2;

        armRight = -x/2 + y/2;

        return new double[] {armLeft, armRight};
    }
    public static float[] getOr(SensorEvent event) {
        float[] rotVec = new float[3];
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            rotVec = event.values;
        }

        if (rotVec != null) {  //make sure we have it before calling stuff
            float orientation[] = new float[3];
            SensorManager.getRotationMatrixFromVector(rotVec, orientation);
            return orientation;
        }
        return null;
    }

    public static double driveArmTrack(double speed, double ratio){
        //ratio is trackmotor rotations/arm motor rotations for the track to stay still
        double trackSpeed;
        trackSpeed = speed * ratio;
        return trackSpeed;
    }
}
