package com.newsblock.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class DaoSessionPool {

	private static Logger log = Logger.getLogger(DaoSessionPool.class);

	private static ArrayList<DaoSession> listDaoSession = new ArrayList<>();

	public static DaoSession createSession() {

		removeDaoSessions();

		int session_diff = listDaoSession.size() - 20;
		if (session_diff > 0) {
			int wait_time = session_diff * 200;
			try {
				Thread.sleep(wait_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		DaoSession ds = getFreeDaoSession();

		if (ds == null) {
			ds = DaoSession.createSession2();
			listDaoSession.add(ds);
		}

		log.info("total db sessions : " + listDaoSession.size());
		System.out.println("total db sessions : " + listDaoSession.size());

		return ds;
	}

	private static boolean flag_remove = false;

	private static void removeDaoSessions() {

		if (!flag_remove) {
			flag_remove = true;

			try {

				long presentTime = System.currentTimeMillis();

				ArrayList<DaoSession> removelistDaoSession = new ArrayList<>();

				for (DaoSession dsin : listDaoSession) {
					long diff = presentTime - dsin.sessionTime;
					if (diff > 120000) {
						if (!dsin.flag_running) {
							removelistDaoSession.add(dsin);
							dsin.completeClose();
						} else if (diff > 900000) {
							removelistDaoSession.add(dsin);
							dsin.completeClose();
						}
					}
				}

				System.out.println("remove sessions : " + removelistDaoSession.size());
				log.info("remove sessions : " + removelistDaoSession.size());

				for (DaoSession dsin : removelistDaoSession) {
					listDaoSession.remove(dsin);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			flag_remove = false;
		}

	}

	private static DaoSession getFreeDaoSession() {
		DaoSession ds = null;
		long presentTime = System.currentTimeMillis();
		for (DaoSession dsin : listDaoSession) {
			long diff = presentTime - dsin.sessionTime;
			if (diff < 90000) {
				if (!dsin.flag_running) {
					ds = dsin;
					ds.flag_running = true;
					return ds;
				}
			}
		}
		return ds;
	}

}
