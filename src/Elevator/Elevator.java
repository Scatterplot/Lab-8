package Elevator;

/**
 * Created by Nathan Bingham on 2/14/15.
 */

public class Elevator
{
    public static final int MAX_CAPACITY = 10;
    public static final int NUM_FLOORS = 7;

    public Floor[] floors;
    private int passengerCount;
    private int currentFloor;
    private boolean direction; //True = Up, False = Down
    private int[] passengerTargets;
    private boolean[] stops;

    public Elevator()
    {
        this.floors = new Floor[NUM_FLOORS];

        for (int i = 0; i < NUM_FLOORS; i++)
        {
            this.floors[i] = new Floor(i);
        }

        this.passengerCount = 0;
        this.currentFloor = 0;
        this.direction = true; // True = Up
        this.passengerTargets = new int[NUM_FLOORS];
        this.stops = new boolean[NUM_FLOORS];
    }

    public void move(int times)
    {
        System.out.println(this.toString());
        this.stop();

        for (int i = 0; i < times; i++)
        {
            // First, increment/decrement the floor based on direction
            if (this.direction)
            {
                this.currentFloor++;
            }
            else
            {
                this.currentFloor--;
            }

            // Check to see if the current floor is valid, and change direction if min/max
            if (this.currentFloor >= NUM_FLOORS-1)
            {
                this.currentFloor = NUM_FLOORS-1;
                this.direction = false;
            }
            else if (this.currentFloor <= 0)
            {
                this.currentFloor = 0;
                this.direction = true;
            }

            System.out.println(this.toString());

            // Check to see if the current floor needs to be stopped at
            if (this.stops[currentFloor])
            {
                this.stop();
            }
        }
    }

    public void stop()
    {
        this.stops[this.currentFloor] = false;
        System.out.println("Passengers unloaded: "+this.passengerTargets[this.currentFloor]);
        this.passengerCount -= this.passengerTargets[this.currentFloor];
        this.passengerTargets[this.currentFloor] = 0;
        int previousCount = this.passengerCount;
        this.floors[this.currentFloor].loadPassengers(this);
        System.out.println("Passengers loaded: "+(this.passengerCount-previousCount));

        if (this.passengerCount == 0) //Failsafe so we don't miss passengers
        {
            for (int i = 0; i < NUM_FLOORS; i++)
            {
                this.stops[i] = true;
            }
        }
    }

    public void boardPassenger(Passenger passenger) throws ElevatorFullException
    {
        if (passengerCount < MAX_CAPACITY)
        {
            this.passengerTargets[passenger.floor]++;
            this.stops[passenger.floor] = true;
            this.passengerCount++;
        }
        else
        {
            throw new ElevatorFullException("Error: Elevator full");
        }
    }

    public String toString()
    {
        return "\r\nCurrent Floor: " + (this.currentFloor + 1) + "\r\nCurrent Passengers: " + this.passengerCount + "\r\nDirection: " + (this.direction ? "Up" : "Down");
    }

    static public void main(String args[])
    {
        Elevator elev = new Elevator();
        
        System.out.println("\n-- Generated Manifest --");
        for (int i = NUM_FLOORS - 1; i >= 0; i--)
        {
            System.out.println(elev.floors[i].toString());
        }
        
        System.out.println("\n-- Simulation Start --");
        elev.move(20);
    }
    
    public static class ElevatorFullException extends Exception
    {
        public ElevatorFullException(String message)
        {
            super(message);
        }
        
        public ElevatorFullException()
        {
            super();
        }
    }
}
