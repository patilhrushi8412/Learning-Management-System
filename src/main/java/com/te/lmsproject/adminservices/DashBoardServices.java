package com.te.lmsproject.adminservices;

import java.util.Map;

public interface DashBoardServices {

	Map<String, Float> pichart(String batchId);

	Map<String, Long> barGraph(String batchId);

	Map<String, Long> barGraphDegree(String batchId);

	Map<String, Long> barGraphforExperience(String batchId);

	Map<String, Long> piChartforBatchPerformance(String batchId);

}
