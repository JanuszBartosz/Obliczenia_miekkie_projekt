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

    protected void stopJump(){
        if(jump != null && jump.isAlive()){
            jump.interrupt();
            jump = null;
        }
    }

    public void startFastForward(){
        stopFastForward();
        stopJump();
        wasRunning = isRunning();
        pause();

        fastForward = new FastForwardThread();
        fastForward.start();
    }

    public void stopFastForward(){
        if(fastForward != null && fastForward.isAlive()){
            fastForward.interrupt();
            fastForward = null;
        }

        if(wasRunning){
            wasRunning = false;
            resume();
        }
    }

    public boolean isFastForwarding(){
        return fastForward != null && fastForward.isAlive();
    }

    public boolean isJumping(){
        return isJumping;
    }

    public void reset(){
        stopJump();
        stopFastForward();
        cancel();
        onReset();
    }

    @Override
    protected void onPreFinish() {
        // Cleanup
        stopJump();
        stopFastForward();
    }

    protected abstract void onReset();

    private Thread fastForward;
    private Thread jump;
    private boolean isJumping;
    private boolean wasRunning;

    private class JumpThread extends Thread{
        private final static long TICKS_TO_JUMP = 1;

        public JumpThread(long ticks){
            this.ticks = ticks;
            continueJumping = true;
        }

        @Override
        public void run(){
            for (long i = 0; i < ticks && continueJumping && isStarted(); i++){
                executeTicks(TICKS_TO_JUMP);
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
        private final static long TICKS_TO_FORWARD = 1;

        public FastForwardThread(){
            continueFastForward = true;
        }

        @Override
        public void run(){
            while(continueFastForward && isStarted()){
                executeTicks(TICKS_TO_FORWARD);
            }
        }

        @Override
        public void interrupt(){
            continueFastForward = false;
        }

        private boolean continueFastForward;
    }
}
