import java.util.Random;

public class DifferentSpeedNoise implements NoiseMachine{

    double speedLimit = 5;
    double[] dRL = {0, 5};
    Random r = new Random();

    public DifferentSpeedNoise() {
        dRL[0] = dRL[0] + (dRL[1] - dRL[0]) * r.nextDouble();
    }

    public DifferentSpeedNoise(double sl, double[] dRL){
        speedLimit=sl;
        dRL[0] = dRL[0] + (dRL[1] - dRL[0]) * r.nextDouble();
    }

    public double[] generateNoise(){
        double[] noise = new double[2];
        noise[1] = dRL[0];
        noise[0] = speedLimit* r.nextDouble();
        return noise;
    }

}