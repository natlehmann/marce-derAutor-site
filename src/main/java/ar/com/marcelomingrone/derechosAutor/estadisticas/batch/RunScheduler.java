package ar.com.marcelomingrone.derechosAutor.estadisticas.batch;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class RunScheduler {

	private static Log log = LogFactory.getLog(RunScheduler.class);

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("importacionJob")
	private Job importacionJob;

	public void run() {

		try {

			Date dateParam = new Date();
			JobParameters param = new JobParametersBuilder().addDate("fechaEjecucion",
					dateParam).toJobParameters();

			log.info("Iniciando ejecución de importador de datos - " + dateParam);

			JobExecution execution = jobLauncher.run(importacionJob, param);
			log.info("Finaliza ejecución de importador de datos con Status : " + execution.getStatus());

		} catch (Exception e) {
			log.error("Error en el proceso de importacion de datos", e);
		}

	}

}
