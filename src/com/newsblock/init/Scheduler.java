/**
 * 
 */
package com.newsblock.init;

import com.newsblock.threads.NewsETLThread;

/**
 * @author lnb
 *
 */
public class Scheduler {

	private NewsETLThread newsETLThread;

	public void start() {

		newsETLThread = new NewsETLThread();
		newsETLThread.start();

		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}

		}

	}

}
