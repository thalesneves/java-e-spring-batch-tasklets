package br.com.springbatch.steps;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import br.com.springbatch.model.Line;
import br.com.springbatch.utils.FileUtils;

/**
 * classe responsável por escrever nomes e idades em um arquivo de saída.
 * a tarefa do LinesWriter é revisar a lista de linhas.
 *
 */
public class LinesWriter implements Tasklet, StepExecutionListener {
	
	private static final Logger logger = Logger.getLogger(LinesWriter.class.getName());
	
	private List<Line> lines;
    private FileUtils fu;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		
		ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        this.lines = (List<Line>) executionContext.get("lines");
        fu = new FileUtils("csv/output.csv");
        logger.info("Lines Writer initialized.");
	}
	
	/*
	 * Este método é onde se adiciona a lógica para cada etapa. 
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		for (Line line : lines) {
            fu.writeLine(line);
            logger.info("Wrote line " + line.toString());
        }
		
        return RepeatStatus.FINISHED;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		fu.closeWriter();
        logger.info("Lines Writer ended.");
       
        return ExitStatus.COMPLETED;
	}

}
