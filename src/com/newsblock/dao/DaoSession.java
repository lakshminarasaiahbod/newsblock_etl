package com.newsblock.dao;

import java.io.File;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * @author sravanpasunoori
 *
 */
public class DaoSession {

	private static boolean flag_factory = false;
	private static StandardServiceRegistry ssr;
	private static Metadata meta;
	private static SessionFactory factory;
	public Session session;
	public Transaction t;
	public boolean flag_running = false;
	public boolean flag_closed = false;

	private static int sessionIdIndex = 0;
	private int sessionId = 0;
	public long sessionTime = 0;

	private static void init() {
		if (!flag_factory) {
			flag_factory = true;
			ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
			meta = new MetadataSources(ssr).getMetadataBuilder().build();
			factory = meta.getSessionFactoryBuilder().build();
		} else {
			boolean flag = true;
			while (flag) {
				if (factory == null) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					flag = false;
				}
			}
		}
	}

	private static void test() {
		try {
			File file = new File("../jotter/test.txt");
			file.createNewFile();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static DaoSession createSession2() {

		init();

		DaoSession ds = new DaoSession();

		ds.flag_running = true;

		ds.session = factory.openSession();
		ds.sessionId = sessionIdIndex++;
		ds.sessionTime = System.currentTimeMillis();
		// log.info("creating ds.sessionId : "+ds.sessionId);

		return ds;
	}

	public static DaoSession createTransaction2() {

		DaoSession ds = createSession2();
		ds.t = ds.session.beginTransaction();

		return ds;
	}

	public void close() {
		// long presentTime = System.currentTimeMillis();
		// log.info("closing ds.sessionId : "+sessionId+" : after :"+(presentTime -
		// sessionTime));
		session.clear();
		// if(t!=null) {
		// session.close();
		// factory.close();
		// ssr.close();
		// }
		flag_running = false;
	}

	public void completeClose() {
		// long presentTime = System.currentTimeMillis();
		// log.info("complete closing ds.sessionId : "+sessionId+" : after
		// :"+(presentTime - sessionTime));
		session.close();
		// factory.close();
		// ssr.close();
		flag_running = false;
		flag_closed = true;
	}

}
