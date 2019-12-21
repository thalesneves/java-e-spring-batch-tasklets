package br.com.springbatch.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import br.com.springbatch.model.Line;
import br.com.springbatch.utils.FileUtils;

/**
 * classe responsável pela leitura dos dados do arquivo de entrada.
 *
 */
public class LinesReader implements Tasklet, StepExecutionListener {
	
	private static final Logger logger = Logger.getLogger(LinesReader.class.getName());
	
	private List<Line> lines;
    private FileUtils fu;

    @Override
    public void beforeStep(StepExecution stepExecution) {

    	lines = new ArrayList<>();
        fu = new FileUtils("input/input.csv");
        logger.info("Lines Reader initialized.");
    }
    
	/*
	 * Este método é onde se adiciona a lógica para cada etapa. 
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		Line line = fu.readLine();
        
		while (line != null) {
            lines.add(line);
            logger.info("Read line: " + line.toString());
            line = fu.readLine();
        }
		
        return RepeatStatus.FINISHED;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		fu.closeReader();
        stepExecution.getJobExecution().getExecutionContext().put("lines", this.lines);
        logger.info("Lines Reader ended.");
        
        return ExitStatus.COMPLETED;
	}
	
}
