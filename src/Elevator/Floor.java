package Elevator;
/**
 * Created by Nathan Bingham on 2/14/15.
 */

import Elevator.Elevator.ElevatorFullException;

import java.util.Random;

public class Floor
{
    public static final int MAX_PASSENGERS = 20;

    private int floorNum;
    private int nextPassenger;
    private Passenger[] passengers;

    public Floor(int floor)
    {
        this.floorNum = floor+1;
        this.nextPassenger = 0;
        this.passengers = new Passenger[MAX_PASSENGERS];

        Random random = new Random();

        for (int i = 0; i < MAX_PASSENGERS; i++)
        {
            if (random.nextBoolean() && random.nextBoolean())
            {
                int newFloor;
                do
                {
                    newFloor = random.nextInt() % Elevator.NUM_FLOORS;
                } while (newFloor < 0 || newFloor > Elevator.NUM_FLOORS);

                this.newPassenger(new Passenger(newFloor));
            }
        }
    }

    public void loadPassengers(Elevator elev)
    {
        int i = this.nextPassenger - 1;
        for (; i >= 0; i--)
        {
            try
            {
                elev.boardPassenger(passengers[i]);
            }
            catch (ElevatorFullException efe)
            {
                break;
            }
        }

        this.nextPassenger = i;
    }

    public void newPassenger(Passenger passenger)
    {
        if (this.nextPassenger < this.MAX_PASSENGERS)
        this.passengers[this.nextPassenger] = passenger;
        this.nextPassenger++;
    }
    
    public String toString()
    {
        return "Floor: "+this.floorNum+", Passengers: "+this.nextPassenger;
    }
}
