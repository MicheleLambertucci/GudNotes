import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Animator implements ActionListener {
    Vector2 final_position;
    double initialDistance;
    double speed;

    Animator(Vector2 start_position, Vector2 final_position, double speed){
        this.final_position = final_position;
        initialDistance = final_position.subtract(start_position).getMagnitude();
        this.speed = speed;
    }

    // returns the vector to be added to the object now moving
    Vector2 step(Vector2 current_position){
        Vector2 distance = final_position.subtract(current_position);
        Vector2 ret = distance.getDirection();
        double step_length = speed(distance.getMagnitude() / initialDistance);
        ret = ret.multiply(step_length);
        return ret;
    }

    // Returns speed given relative distance
    // Relative distance contains a value between [0 and 1]
    private double speed(double relative_distance){
        // Normalize x to be in [-0.5, 0.5]
        double normalized_x = relative_distance - 0.5;

        // Return Gaussian to ease
        // Max velocity based on initialDistance, so the movement lasts exactly the same for every distance
        return Gaussian(normalized_x, initialDistance * speed, 0.1);
    }

    // Gaussian function with peak 'height' and variance 'var'
    private double Gaussian(double x, double height, double var){
        return height * Math.exp(-(x*x) / (2 * var));
    }

    public abstract void actionPerformed(ActionEvent e);
}
