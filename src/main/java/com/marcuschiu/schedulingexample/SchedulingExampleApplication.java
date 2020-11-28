package com.marcuschiu.schedulingexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class SchedulingExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulingExampleApplication.class, args);
	}

	/**
	 * In this case, the duration between the end of the last execution and the start of the
	 * next execution is fixed. The task always waits until the previous one is finished.
	 * This option should be used when it’s mandatory that the previous execution is completed
	 * before running again.
	 */
	@Scheduled(fixedDelay = 1000)
	public void test() {
		System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
	}

	/**
	 * This option should be used when each execution of the task is independent.
	 * Note that scheduled tasks don't run in parallel by default. So even if we used
	 * fixedRate, the next task won't be invoked until the previous one is done
	 */
	@Scheduled(fixedRate = 1000)
	public void scheduleFixedRateTask() {
		System.out.println("Fixed rate task - " + System.currentTimeMillis() / 1000);
	}

	/**
	 * If we want to support parallel behavior in scheduled tasks,
	 * we need to add the @Async annotation
	 * Now this asynchronous task will be invoked each second, even
	 * if the previous task isn't done
	 * @throws InterruptedException
	 */
	@Async
	@Scheduled(fixedRate = 1000)
	public void scheduleFixedRateTaskAsync() throws InterruptedException {
		System.out.println("Fixed rate task async - " + System.currentTimeMillis() / 1000);
		Thread.sleep(2000);
	}

	/**
	 * Note how we're using both fixedDelay as well as initialDelay in this example.
	 * The task will be executed the first time after the initialDelay value, and it will
	 * continue to be executed according to the fixedDelay.
	 * This option is convenient when the task has a setup that needs to be completed
	 */
	@Scheduled(cron = "0 15 10 15 * ?", zone = "Europe/Paris")
	public void scheduleFixedRateWithInitialDelayTask() {
		long now = System.currentTimeMillis() / 1000;
		System.out.println("Fixed rate task with one second initial delay - " + now);
	}

	/*
	 *
	 * A fixedDelay task:
	 * - @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
	 * A fixedRate task:
	 * - @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
	 * A cron expression based task:
	 * - @Scheduled(cron = "${cron.expression}")
	 */
}
