
public class Engine
{

    public static final double g = 9.81;
    public static double friction;
    public static final double h = 0.1;
    public static double xh;
    public static double yh;
    public static double vx_h;
    public static double vy_h;

    public static void Calculate(Ball ball, Field fields)
    {
        x = ball.position.x;
        y = ball.position.y;
        vx = ball.velocity.x;
        vy = ball.velocity.y;

         xh = x + h * vx;
         yh = y + h * vy;

         vx_h = vx + h * ForceX();
         vy_h = vy + h * ForceY();

        ball(xh, yh, vx_h, vy_h);
    }

    public static double ForceX()
    {
        double Fx = ((-g) * (xh)) - (friction * g * vx) / Math.sqrt(vx ^ 2 + vy ^ 2);
        return Fx;
    }
    public static double ForceY()
    {
        double Fy = ((-g) * (yh)) - (friction * g * vy) / Math.sqrt(vx ^ 2 + vy ^ 2);
        return Fy;
    }
}
