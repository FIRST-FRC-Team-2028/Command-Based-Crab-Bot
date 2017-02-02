package org.usfirst.frc.team2028.robot;


public class Parameters {
    public enum CanId {
        DRIVE_RIGHT_MASTER (11, false),
        DRIVE_RIGHT_FOLLOWER (43, false),
        DRIVE_LEFT_MASTER (32, false),
        DRIVE_LEFT_FOLLOWER (51, false),
        STEERING_LEFT_FRONT (20, false),
        STEERING_RIGHT_FRONT (44, false),
        STEERING_LEFT_REAR (31, false),
        STEERING_RIGHT_REAR (22, false),
        SHOOTER_WHEEL (0, false),
        SHOOTER_CONVEYOR (0, false),
        CLIMBER_MOTOR (0, false);

        private int canID;

        private boolean reversed;

        private CanId(int id, boolean rev) {
            canID = id;
            reversed = rev;
        }

        public int getId() {
            return canID;
        }

        public boolean isReversed() {
            return reversed;
        }

    }

    public enum SteeringOffset {
        LEFT_FRONT (-0.32), //20
        LEFT_REAR (0.47), //31
        RIGHT_FRONT (0.21), //44
        RIGHT_REAR (0.49); //22

        private double offset;

        private SteeringOffset(double offset) {
            this.offset = offset;
        }

        public double getOffset() {
            return offset;
        }

    }

    public enum SideOfRobot {
        RIGHT,
        LEFT;
    }

    public enum Buttons {
       SWEARVE(2),
       SPIN_ON_AXIS(1);
       
       private int id;
       
       private Buttons(int id)
       {
    	   this.id = id;
       }
       
       public int getID()
       {
    	   return id;
       }
    }

    public enum PneumaticChannel {
        LOW_GEAR(0),
        HIGH_GEAR(7);
        
        private int channel;
        
        private PneumaticChannel(int channel)
        {
        	this.channel = channel;
        }
        
        public int getChannel()
        {
        	return channel;
        }
    }

}
