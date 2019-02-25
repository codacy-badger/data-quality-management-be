package com.bbahaida.dataqualitymanagement.web;

import com.bbahaida.dataqualitymanagement.utils.RoutingConstants;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping(RoutingConstants.JOB_ROUTE)
public class JobInvokerController {
    private static final String JOB_PARAM = "job";
    private JobLauncher jobLauncher;
    private JobRegistry jobRegistry;

    public JobInvokerController(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @GetMapping("launch")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void launch(@RequestParam(JOB_PARAM) String job,
                       HttpServletRequest request) throws Exception {
        JobParametersBuilder builder = extractParameters(
                request
        );
        jobLauncher.run(
                jobRegistry.getJob(job),
                builder.toJobParameters()
        );
    }

    private JobParametersBuilder extractParameters(
            HttpServletRequest request) {
        JobParametersBuilder builder = new JobParametersBuilder();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (!JOB_PARAM.equals(paramName)) {
                builder.addString(paramName, request.getParameter(paramName));
            }
        }
        return builder;
    }
}
