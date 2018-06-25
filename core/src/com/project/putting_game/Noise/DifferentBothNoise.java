import java.util.Random;

public class DifferentBothNoise implements NoiseMachine{

    double speedLimit = 5;
    double[] dRL = {0, 5};
    Random r = new Random();

    public DifferentBothNoise() {}

    public DifferentBothNoise(double sl, double[] dRLIN){
        speedLimit=sl;
        dRL = dRLIN;
    }

    public double[] generateNoise(){
        double[] noise = new double[2];
        noise[0] = speedLimit* r.nextDouble();
        noise[1] = dRL[0] + (dRL[1] - dRL[0]) * r.nextDouble();
        return noise;
    }

}