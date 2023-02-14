package com.te.lmsproject.admincontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.te.lmsproject.adminservices.DashBoardServices;
import com.te.lmsproject.lmsresponce.Responce;
import static com.te.lmsproject.lmsconstants.Constants.*;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DashBoardController {

	@Autowired
	private DashBoardServices services;

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/piChart/{batchId}")
	public ResponseEntity<Responce> pichart(@PathVariable String batchId) {
		log.info(PI_CHART_FOR_GENDER);
		Map<String, Float> chart = services.pichart(batchId);
		return new ResponseEntity<Responce>(new Responce(false, PI_CHART_FOR_GENDER, chart), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/barGraph/{batchId}")
	public ResponseEntity<Responce> barGraph(@PathVariable String batchId) {
		log.info(BAR_GRAPH_FOR_YEAR_OF_PASSING);
		Map<String, Long> graph = services.barGraph(batchId);
		return new ResponseEntity<Responce>(new Responce(false, BAR_GRAPH_FOR_YEAR_OF_PASSING, graph), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/barGraphforDegree/{batchId}")
	public ResponseEntity<Responce> barGraphDegree(@PathVariable String batchId) {
		log.info(BAR_GRAPH_FOR_EXPERIENCE);
		Map<String, Long> graph = services.barGraphDegree(batchId);
		return new ResponseEntity<Responce>(new Responce(false, BAR_GRAPH_FOR_EXPERIENCE, graph), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/barGraphforExperience/{batchId}")
	public ResponseEntity<Responce> barGraphforExperience(@PathVariable String batchId) {
		log.info(BAR_GRAPH_FOR_EMPLOYEE_DEGREE);
		Map<String, Long> graph = services.barGraphforExperience(batchId);
		return new ResponseEntity<Responce>(new Responce(false, BAR_GRAPH_FOR_EMPLOYEE_DEGREE, graph), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin')")
	@GetMapping("/piChartforBatchPerformance/{batchId}")
	public ResponseEntity<Responce> piChartforBatchPerformance(@PathVariable String batchId) {
		log.info(PI_CHART_FOR_BATCH_PERFORMANCE);
		Map<String, Long> chart = services.piChartforBatchPerformance(batchId);
		return new ResponseEntity<Responce>(new Responce(false, PI_CHART_FOR_BATCH_PERFORMANCE, chart), HttpStatus.OK);
	}
}
