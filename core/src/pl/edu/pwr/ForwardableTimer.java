package pl.edu.pwr;

public abstract class ForwardableTimer extends Timer {
    public ForwardableTimer(long interval, long duration){
        super(interval, duration);
    }

    public void jumpTicks(long ticks){
        if(ticks > 0) {
            isJumping = true;
            boolean wasRunning = isRunning();
            pause();

            jump = new JumpThread(ticks);
            jump.start();

            try {
                jump.join();
            } catch (InterruptedException e) {}
            jump = null;

            if(wasRunning){
                resume();
            }
            isJumping = false;
        }
    }

    public void startFastForward(){
        stopFastForward();
        stopJump();
        isFastForwarding = true;
        boolean wasRunning = isRunning();
        pause();

        fastForward = new FastForwardThread();

        if(wasRunning){
            resume();
        }
        fastForward.start();
    }

    public void stopFastForward(){
        if(fastForward != null && fastForward.isAlive()){
            fastForward.interrupt();
            fastForward = null;
            isFastForwarding = false;
        }
    }

    public boolean isFastForwarding(){
        return isFastForwarding;
    }

    public boolean isJumping(){
        return isJumping;
    }

    private void stopJump(){
        if(jump != null && jump.isAlive()){
            jump.interrupt();
            jump = null;
        }
    }

    private Thread fastForward;
    private Thread jump;
    private boolean isJumping;
    private boolean isFastForwarding;

    private class JumpThread extends Thread{
        public JumpThread(long ticks){
            this.ticks = ticks;
            continueJumping = true;
        }

        @Override
        public void run(){
            for (long i = 0; i < ticks && continueJumping; i++){
                onTick();
            }
        }

        @Override
        public void interrupt(){
            continueJumping = false;
        }

        private final long ticks;
        private boolean continueJumping;
    }

    private class FastForwardThread extends Thread{
        public FastForwardThread(){
            continueFastForward = true;
        }

        @Override
        public void run(){
            while(continueFastForward){
                onTick();
            }
        }

        @Override
        public void interrupt(){
            continueFastForward = false;
        }

        private boolean continueFastForward;
    }
}
