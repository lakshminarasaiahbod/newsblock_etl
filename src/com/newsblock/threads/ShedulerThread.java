/**
 * 
 */
package com.newsblock.threads;

/**
 * @author sravanpasunoori
 *
 */
public class ShedulerThread extends Thread {

	protected boolean flag_continue = true;
	
	protected int intial_wait = 5000;

	public ShedulerThread(String name) {
		super(name);
	}

	public boolean isFlag_continue() {
		return flag_continue;
	}

	public void setFlag_continue(boolean flag_continue) {
		this.flag_continue = flag_continue;
	}

}
