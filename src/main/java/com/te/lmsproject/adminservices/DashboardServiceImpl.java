package com.te.lmsproject.adminservices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.te.lmsproject.employeeentity.Employee;
import com.te.lmsproject.lmscustomexception.CustomException;
import com.te.lmsproject.mentorentity.EmployeeDetail;
import com.te.lmsproject.mentorentity.FinalBatch;
import com.te.lmsproject.mentorrepository.EmployeeDetailsRepo;
import com.te.lmsproject.mentorrepository.FinalBatchRepo;
import static com.te.lmsproject.lmsconstants.Constants.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DashboardServiceImpl implements DashBoardServices {

	@Autowired
	private FinalBatchRepo repo;

	@Autowired
	private EmployeeDetailsRepo emprepo;

	@Override
	public Map<String, Float> pichart(String batchId) {
		try {
			log.info(GETTING_PICHART_FOR_GENDERS_IN_BATCH);
			Optional<FinalBatch> batch = repo.findByBatchId(batchId);
			if (!batch.isPresent()) {
				log.error(BATCH_YOU_WANT_IS_NOT_PRESENT, batchId);
				throw new CustomException(BATCH_YOU_WANT_IS_NOT_PRESENT);
			} else {
				List<Employee> emp = batch.get().getEmp();
				long male = emp.stream().filter(t -> t.getGender().equalsIgnoreCase("male")).count();
				long female = emp.stream().filter(t -> t.getGender().equalsIgnoreCase("female")).count();
				Float malePercent=Float.valueOf(male)/Float.valueOf( batch.get().getEmp().size())*100f;
	            Float femalePercent=Float.valueOf(female)/Float.valueOf( batch.get().getEmp().size())*100f;
	            
	                Map<String, Float> chart = new HashMap<>();
	                chart.put("Male", malePercent);
	                chart.put("Female", femalePercent);
	                
	         	log.info(GETTING_PICHART_FOR_GENDERS_SUCCESFULLY);
				return chart;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Long> barGraph(String batchId) {
		try {
			log.info(GETTING_BAR_GRAPH_FOR_YEAR_OF_PASSING);
			Optional<FinalBatch> batch = repo.findByBatchId(batchId);
			if (!batch.isPresent()) {
				log.error(BATCH_YOU_WANT_IS_NOT_PRESENT, batchId);
				throw new CustomException(BATCH_YOU_WANT_IS_NOT_PRESENT);
			} else {
				List<Employee> emp = batch.get().getEmp();
				Map<String, Long> chart = new HashMap<>();
				chart.put("2019", emp.stream().filter(t -> t.getEducationDetails().getYearOfPassing() == 2019).count());
				chart.put("2020", emp.stream().filter(t -> t.getEducationDetails().getYearOfPassing() == 2020).count());
				chart.put("2021", emp.stream().filter(t -> t.getEducationDetails().getYearOfPassing() == 2021).count());
				chart.put("2022", emp.stream().filter(t -> t.getEducationDetails().getYearOfPassing() == 2022).count());
				chart.put("2023", emp.stream().filter(t -> t.getEducationDetails().getYearOfPassing() == 2023).count());
				log.info(GETTING_BARGRAPH_FOR_YEAR_OF_PASSING_SUCCESFULLY, batchId);
				return chart;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Long> barGraphDegree(String batchId) {
		try {
			log.info(GETTING_BARGRAPH_FOR_EDUCATION_TYPE);
			Optional<FinalBatch> batch = repo.findByBatchId(batchId);
			if (!batch.isPresent()) {
				log.error(BATCH_YOU_WANT_IS_NOT_PRESENT, batchId);
				throw new CustomException(BATCH_YOU_WANT_IS_NOT_PRESENT);
			} else {
				List<Employee> emp = batch.get().getEmp();
				Map<String, Long> graph = new HashMap<>();
				graph.put("Graduation",
						emp.stream()
								.filter(t -> t.getEducationDetails().getEducationType().equalsIgnoreCase("Graduation"))
								.count());
				graph.put("Post Graduation",
						emp.stream().filter(
								t -> t.getEducationDetails().getEducationType().equalsIgnoreCase("Post Graduation"))
								.count());
				log.info(GETTING_BARGRAPH_FOR_EDUCATION_TYPE_SUCCESSFULLY, batchId);
				return graph;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Long> barGraphforExperience(String batchId) {
		try {
			log.info(GETTING_BARGRAPH_FOR_EXPERIENCE);
			Optional<FinalBatch> batch = repo.findByBatchId(batchId);
			if (!batch.isPresent()) {
				log.error(BATCH_YOU_WANT_IS_NOT_PRESENT, batchId);
				throw new CustomException(BATCH_YOU_WANT_IS_NOT_PRESENT);
			} else {
				List<Employee> emp = batch.get().getEmp();
				Map<String, Long> graph = new HashMap<>();
				List<Stream<Integer>> collect = emp.stream().map(e -> e.getExperiences().stream().map(t -> {
					return t.getYearsOfExperience();
				})).collect(Collectors.toList());
				List<Integer> collect2 = collect.stream().flatMap(e -> e).collect(Collectors.toList());
				graph.put("Fresher", collect2.stream().filter(t -> t == 0).count());
				graph.put("1", collect2.stream().filter(t -> t == 1).count());
				graph.put("2", collect2.stream().filter(t -> t == 2).count());
				graph.put("5", collect2.stream().filter(t -> t == 5).count());
				graph.put("10", collect2.stream().filter(t -> t == 10).count());
				log.info(GETTING_BARGRAPH_FOR_EXPERIENCE_SUCCESFULLY_FOR, batchId);
				return graph;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public Map<String, Long> piChartforBatchPerformance(String batchId) {
		try {
			log.info(GETTING_PI_CHART_FOR_BATCH_PERFORMANCE, batchId);
			Optional<FinalBatch> batch = repo.findByBatchId(batchId);
			if (!batch.isPresent()) {
				log.error(BATCH_YOU_WANT_IS_NOT_PRESENT + " " + batchId);
				throw new CustomException(BATCH_YOU_WANT_IS_NOT_PRESENT);
			} else {
				List<EmployeeDetail> list = emprepo.findByBatchId(batchId);
				List<List<String>> collect = list.stream().map(e -> e.getMockRatings().stream().map(t -> {
					return t.getOverAllFeedback();
				})).collect(Collectors.toList()).stream().flatMap(e -> e).collect(Collectors.toList());
				List<String> collect2 = collect.stream().flatMap(t -> t.stream()).collect(Collectors.toList());
				Map<String, Long> graph = new HashMap<>();
				graph.put("Excellent", collect2.stream().filter(t -> t.equalsIgnoreCase("Excellent")).count());
				graph.put("Good", collect2.stream().filter(t -> t.equalsIgnoreCase("Good")).count());
				graph.put("Above Average", collect2.stream().filter(t -> t.equalsIgnoreCase("Above Average")).count());
				graph.put("Average", collect2.stream().filter(t -> t.equalsIgnoreCase("Average")).count());
				graph.put("Below Average", collect2.stream().filter(t -> t.equalsIgnoreCase("Below Average")).count());
				log.info(GETTING_PI_CHART_FOR_BATCH_PERFORMANCE_SUCCESFULLY_FOR, batchId);
				return graph;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
