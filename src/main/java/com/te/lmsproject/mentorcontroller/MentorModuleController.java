package com.te.lmsproject.mentorcontroller;

import static com.te.lmsproject.lmsconstants.Constants.ADDING_MOCK;
import static com.te.lmsproject.lmsconstants.Constants.ASSIGNING_BATCH;
import static com.te.lmsproject.lmsconstants.Constants.ASSIGNING_EMPLOYEE_RATINGS;
import static com.te.lmsproject.lmsconstants.Constants.BATCH_ASSIGN_SUCCESFULLY;
import static com.te.lmsproject.lmsconstants.Constants.BATCH_LIST;
import static com.te.lmsproject.lmsconstants.Constants.CHANGE_STATUS_SUCCESFULLY;
import static com.te.lmsproject.lmsconstants.Constants.CHANGING_STATUS_OF_EMPLOYEE;
import static com.te.lmsproject.lmsconstants.Constants.EMPLOYEE_RATINGS_ASSIGN_SUCCESFULLY;
import static com.te.lmsproject.lmsconstants.Constants.GETTING_ALL_BATCH_LIST;
import static com.te.lmsproject.lmsconstants.Constants.MOCK_ADDED_SUCCESFULLY;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.te.lmsproject.lmsresponce.Responce;
import com.te.lmsproject.mentordto.AddMockDto;
import com.te.lmsproject.mentordto.ChangeStatusDto;
import com.te.lmsproject.mentordto.EmployeeDetailsDto;
import com.te.lmsproject.mentordto.FinalBatchDto;
import com.te.lmsproject.mentorentity.AddMock;
import com.te.lmsproject.mentorentity.EmployeeDetail;
import com.te.lmsproject.mentorentity.FinalBatch;
import com.te.lmsproject.mentorservices.Mentorservices;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MentorModuleController {

	@Autowired
	Mentorservices services;

	@PreAuthorize("hasAnyRole('Admin','Mentor')")
	@PostMapping("/assignFinalBatch")
	public ResponseEntity<Responce> assignBatch(@RequestBody FinalBatchDto batchDto) {
		log.info(ASSIGNING_BATCH);
		FinalBatch batch = services.assignBatch(batchDto);
		return new ResponseEntity<Responce>(new Responce(false, BATCH_ASSIGN_SUCCESFULLY, batch), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Mentor')")
	@GetMapping("/batchList")
	public ResponseEntity<Responce> getBatchList() {
		log.info(GETTING_ALL_BATCH_LIST);
		List<FinalBatch> batch = services.getBatchList();
		return new ResponseEntity<Responce>(new Responce(false, BATCH_LIST, batch), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Mentor')")
	@PostMapping("/employeeRatings")
	public ResponseEntity<Responce> employeeRatings(@RequestBody EmployeeDetailsDto empdto) {
		log.info(ASSIGNING_EMPLOYEE_RATINGS);
		EmployeeDetail emp = services.employeeRatings(empdto);
		return new ResponseEntity<Responce>(new Responce(false, EMPLOYEE_RATINGS_ASSIGN_SUCCESFULLY, emp),
				HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Mentor')")
	@PostMapping("/addMock")
	public ResponseEntity<Responce> addMock(@RequestBody AddMockDto mockdto) {
		log.info(ADDING_MOCK);
		AddMock emp = services.employeeRatings(mockdto);
		return new ResponseEntity<Responce>(new Responce(false, MOCK_ADDED_SUCCESFULLY, emp), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Mentor')")
	@PostMapping("/changeStatus")
	public ResponseEntity<Responce> changeStatus(@RequestBody ChangeStatusDto dto) {
		log.info(CHANGING_STATUS_OF_EMPLOYEE);
		services.changeStatus(dto);
		return new ResponseEntity<Responce>(new Responce(false, CHANGE_STATUS_SUCCESFULLY, null), HttpStatus.OK);
	}

}
