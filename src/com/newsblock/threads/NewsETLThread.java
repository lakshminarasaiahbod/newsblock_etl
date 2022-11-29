/**
 * 
 */
package com.newsblock.threads;

import java.util.List;

import com.newsblock.code.DataProcessor;
import com.newsblock.dao.ConfigPropertiesRepository;
import com.newsblock.model.ConfigProperties;

/**
 * @author lnb
 *
 */
public class NewsETLThread extends ShedulerThread {

	private DataProcessor processor;

	public NewsETLThread() {
		super("NewsETLThread");
	}

	@Override
	public void run() {

		System.out.println("Starting NewsETLThread ...");
		processor = new DataProcessor();
		try {
			Thread.sleep(intial_wait);
		} catch (InterruptedException e) {
		}
		while (isFlag_continue()) {
			System.out.println("NewsETLThread loop start ...");
			try {
				ConfigPropertiesRepository configPropertiesRepository = new ConfigPropertiesRepository();
				ConfigProperties configProperty = configPropertiesRepository.getMapByName().get("NewsETLThreadLive");
				if(configProperty != null) {
					boolean flag = true;
					if (configProperty != null && configProperty.getValue() == "1") {
						flag = false;
					}
					if (flag) {
						processor.extractdata();
					} else {
						System.out.println("NewsETLThread is pause state ...");
					}
					
				}else {
					System.out.println("NewsETLThread is pause state ...");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("NewsETLThread loop end ...");
			try {
				Thread.sleep(10800000);
			} catch (InterruptedException e) {
			}

		}
	}

}
