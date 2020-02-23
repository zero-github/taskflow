package com.zero.taskflow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.zero.taskflow.raft.ITaskflowServer;

@SpringBootApplication
public class TaskflowApplication implements CommandLineRunner {
	private static Logger logger = LogManager.getLogger(TaskflowApplication.class.getSimpleName());

	@Autowired
    private ApplicationContext applicationContext;

	@Override
    public void run(String... args) throws Exception {
		ITaskflowServer taskflowServer = applicationContext.getBean(ITaskflowServer.class);
		if(!taskflowServer.init()){
			logger.fatal("Failed to init");
		}

		if(!taskflowServer.start()){
			logger.fatal("Failed to start");
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TaskflowApplication.class, args);
	}

}
