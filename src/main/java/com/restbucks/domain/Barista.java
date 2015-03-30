package com.restbucks.domain;

import org.springframework.stereotype.Service;

@Service
public class Barista {

	
	public void process(final Order order) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				order.setStatus("ready");
			}
		}).start();
	}
}
