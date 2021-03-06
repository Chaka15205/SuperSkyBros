package com.relicum.scb.utils.timers;

import com.relicum.scb.ScheduledManager;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * First Created 24/10/13 Start Countdown. Multiple Instances can be run at the
 * same time Uses a ScheduledThreadPool Service
 *
 * @author TheCommunitySurvivalGames
 * @version 0.1
 */
public
@Data
class StartTimer {

    private static final boolean DONT_INTERRUPT_IF_RUNNING = false;
    private static ScheduledExecutorService scheduler = ScheduledManager.getScheduler();
    private final long fInitialDelay;
    private final long fDelayPeriod;
    private final long fShutDownAfter;
    public Integer timeleft;

    public StartTimer(long initial, long period, Integer tl) {
        System.out.println("Current Thread is: " + Thread.currentThread().getName());
        fInitialDelay = initial;
        fDelayPeriod = period;
        timeleft = tl;
        fShutDownAfter = tl.longValue();
        StartTimerAndStop();
    }

    private static void log(String aMsg) {
        System.out.println(aMsg);
    }

    /**
     * Make timer. Used to create a new instance of Start Game Countdown
     *
     * @param initial the initial delay before starting - seconds
     * @param period  the period between executions - seconds
     * @param tl      the max time the timer will run before being shutdown - seconds
     */
    public static void makeTimer(long initial, long period, Integer tl) {
        new StartTimer(initial, period, tl);
    }

    /**
     * Basically Initialise the object
     */
    void StartTimerAndStop() {

        Runnable GameStartTask = new StartGameTimerTask((int) fShutDownAfter);
        ScheduledFuture<?> StartGameFuture = scheduler.scheduleAtFixedRate(GameStartTask, fInitialDelay, fDelayPeriod, TimeUnit.SECONDS);
        Runnable stopStartGameTask = new StopGameStartTask(StartGameFuture);
        scheduler.schedule(stopStartGameTask, fShutDownAfter, TimeUnit.SECONDS);
    }

    /**
     * The actual Task for the Game Timer
     */
    private static final class StartGameTimerTask implements Runnable {

        private Integer timeleft;

        StartGameTimerTask(int i) {
            timeleft = i;

        }

        @Override
        public void run() {

            --timeleft;

            if (this.timeleft.equals(30)) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game is about to start in " + this.timeleft);
            } else if (this.timeleft.equals(25)) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game is about to start in " + this.timeleft);
            } else if (this.timeleft.equals(20)) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game is about to start in " + this.timeleft);
            } else if (this.timeleft.equals(15)) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game is about to start in " + this.timeleft);
            } else if (this.timeleft.equals(10)) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game is about to start in " + this.timeleft);
            } else if (timeleft < 10 && timeleft > 0) {
                Bukkit.broadcastMessage(ChatColor.BLUE + "The game is about to start in " + this.timeleft);
            } else if (timeleft == 0) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "GO GO GO Game Started");
            }

        }
    }

    /**
     * The Task for the future eg ending of the task.
     */
    private final class StopGameStartTask implements Runnable {

        private ScheduledFuture<?> fScheduledFuture;

        StopGameStartTask(ScheduledFuture<?> aSchedFeature) {

            fScheduledFuture = aSchedFeature;

        }

        @Override
        public void run() {
            log("Stopping the Game Start Timer");

            fScheduledFuture.cancel(DONT_INTERRUPT_IF_RUNNING);

            // scheduler.shutdown();
        }
    }
}
