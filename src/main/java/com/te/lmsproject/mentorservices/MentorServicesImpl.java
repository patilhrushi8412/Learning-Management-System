package com.te.lmsproject.mentorservices;

import static com.te.lmsproject.lmsconstants.Constants.ADDING_MOCK;
import static com.te.lmsproject.lmsconstants.Constants.ADDING_MOCK_SUCCESFULLY;
import static com.te.lmsproject.lmsconstants.Constants.ASSIGNING_BATCH;
import static com.te.lmsproject.lmsconstants.Constants.ASSIGNING_EMPLOYEE_RATING;
import static com.te.lmsproject.lmsconstants.Constants.ASSIGNING_EMPLOYEE_RATINGS_SUCCESFULLY;
import static com.te.lmsproject.lmsconstants.Constants.BATCH_ASSIGNED_SUCCESFULLY;
import static com.te.lmsproject.lmsconstants.Constants.CHANGING_STATUS_OF_EMPLOYEE;
import static com.te.lmsproject.lmsconstants.Constants.CHANGING_STATUS_SUCCESFULLY;
import static com.te.lmsproject.lmsconstants.Constants.EMPLOYEE_WHICH_YOU_GIVING_RATING_IS_NOT_PRESENT_ON_THIS_EMPLOYEE_ID;
import static com.te.lmsproject.lmsconstants.Constants.GETTING_FINAL_BATCH_DETAILS_SUCCESFULLY;
import static com.te.lmsproject.lmsconstants.Constants.GETTING_FINAL_BATCH_LIST;
import static com.te.lmsproject.lmsconstants.Constants.NO_ONE_BATCH_IS_AVAILABLE_AT_THE_MOMENT;
import static com.te.lmsproject.lmsconstants.Constants.WHICH_BATCH_YOU_ASSIGN_IS_NOT_PRESENT;
import static com.te.lmsproject.lmsconstants.Constants.WHICH_EMPLOYEE_STATUS_YOU_WANT_TO_CHANGE_IS_NOT_AVAILABLE;
import static com.te.lmsproject.lmsconstants.Constants.WHICH_EMPLOYEE_YOU_ADD_IS_NOT_PRESENT_AND_THAT_EMPLOYEE_ID_IS;
import static com.te.lmsproject.lmsconstants.Constants.WHICH_EMPLOYEE_YOU_ADD_IS_NOT_PRESENT_AND_THAT_EMPLOYEE_ID_IS2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.te.lmsproject.adminentity.Batch;
import com.te.lmsproject.adminrepository.BatchRepo;
import com.te.lmsproject.employeeentity.Employee;
import com.te.lmsproject.employeerepository.EmployeeRepository;
import com.te.lmsproject.lmscustomexception.CustomException;
import com.te.lmsproject.mentordto.AddMockDto;
import com.te.lmsproject.mentordto.ChangeStatusDto;
import com.te.lmsproject.mentordto.EmployeeDetailsDto;
import com.te.lmsproject.mentordto.FinalBatchDto;
import com.te.lmsproject.mentorentity.AddMock;
import com.te.lmsproject.mentorentity.ChangeStatus;
import com.te.lmsproject.mentorentity.EmployeeDetail;
import com.te.lmsproject.mentorentity.FinalBatch;
import com.te.lmsproject.mentorrepository.AddMockRepo;
import com.te.lmsproject.mentorrepository.EmployeeDetailsRepo;
import com.te.lmsproject.mentorrepository.FinalBatchRepo;
import com.te.lmsproject.mentorrepository.StatusChange;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MentorServicesImpl implements Mentorservices {

	@Autowired
	private StatusChange statusRepo;

	@Autowired
	private BatchRepo batchRepo;

	@Autowired
	private FinalBatchRepo repo;

	@Autowired
	private EmployeeRepository emprepo;

	@Autowired
	private EmployeeDetailsRepo employeeDetailsRepo;

	@Autowired
	private AddMockRepo mockrepo;

	@Override
	public FinalBatch assignBatch(FinalBatchDto dto) {
		try {
			log.info(ASSIGNING_BATCH);
			Optional<Batch> findById = batchRepo.findById(dto.getBatchNo());
			System.out.println(findById.get());
			if (!findById.isPresent()) {
				log.error(WHICH_BATCH_YOU_ASSIGN_IS_NOT_PRESENT);
				throw new CustomException(WHICH_BATCH_YOU_ASSIGN_IS_NOT_PRESENT);
			} else {
				FinalBatch batch = new FinalBatch();
				List<Employee> emp = new ArrayList<>();
				for (int i = 0; i < dto.getEmployeeId().size(); i++) {
					Optional<Employee> findByEmployeeId = emprepo.findByEmployeeId(dto.getEmployeeId().get(i));
					if (!findByEmployeeId.isPresent()) {
						log.error(WHICH_EMPLOYEE_YOU_ADD_IS_NOT_PRESENT_AND_THAT_EMPLOYEE_ID_IS
								+ dto.getEmployeeId().get(i));
						throw new CustomException(WHICH_EMPLOYEE_YOU_ADD_IS_NOT_PRESENT_AND_THAT_EMPLOYEE_ID_IS2
								+ dto.getEmployeeId().get(i));
					}
					System.out.println(findByEmployeeId.get());
					emp.add(findByEmployeeId.get());
				}
				BeanUtils.copyProperties(findById.get(), batch);
				BeanUtils.copyProperties(dto, batch);

				batch.setStrength(emp.size());
				batch.setEmp(emp);
				log.info(BATCH_ASSIGNED_SUCCESFULLY);
				FinalBatch save = repo.save(batch);
				return save;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public List<FinalBatch> getBatchList() {
		try {
			log.info(GETTING_FINAL_BATCH_LIST);
			List<FinalBatch> findAll = repo.findAll();
			if (findAll.isEmpty()) {
				log.error(NO_ONE_BATCH_IS_AVAILABLE_AT_THE_MOMENT);
				throw new CustomException(NO_ONE_BATCH_IS_AVAILABLE_AT_THE_MOMENT);
			} else {
				log.info(GETTING_FINAL_BATCH_DETAILS_SUCCESFULLY);
				return findAll;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public EmployeeDetail employeeRatings(EmployeeDetailsDto empdto) {
		try {
			log.info(ASSIGNING_EMPLOYEE_RATING);
			Optional<Employee> employee = emprepo.findByEmployeeId(empdto.getEmployeeId());
			if (!employee.isPresent()) {
				log.error(EMPLOYEE_WHICH_YOU_GIVING_RATING_IS_NOT_PRESENT_ON_THIS_EMPLOYEE_ID);
				throw new CustomException(EMPLOYEE_WHICH_YOU_GIVING_RATING_IS_NOT_PRESENT_ON_THIS_EMPLOYEE_ID);
			} else {
				EmployeeDetail detail = new EmployeeDetail();
				BeanUtils.copyProperties(employee.get(), detail);
				BeanUtils.copyProperties(empdto, detail);
				EmployeeDetail save = employeeDetailsRepo.save(detail);
				log.info(ASSIGNING_EMPLOYEE_RATINGS_SUCCESFULLY);
				return save;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public AddMock employeeRatings(AddMockDto mockdto) {
		try {
			log.info(ADDING_MOCK);
			AddMock mock = new AddMock();
			BeanUtils.copyProperties(mockdto, mock);
			AddMock save = mockrepo.save(mock);
			log.info(ADDING_MOCK_SUCCESFULLY);
			return save;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public void changeStatus(ChangeStatusDto dto) {
		try {
			log.info(CHANGING_STATUS_OF_EMPLOYEE);
			Optional<EmployeeDetail> detail = employeeDetailsRepo.findByEmployeeId(dto.getEmployeeId());
			if (!detail.isPresent()) {
				log.error(WHICH_EMPLOYEE_STATUS_YOU_WANT_TO_CHANGE_IS_NOT_AVAILABLE);
				throw new CustomException(WHICH_EMPLOYEE_STATUS_YOU_WANT_TO_CHANGE_IS_NOT_AVAILABLE);
			} else {
				ChangeStatus changeStatus = new ChangeStatus();
				BeanUtils.copyProperties(dto, changeStatus);
				changeStatus.setEmployeeName(detail.get().getEmployeeName());
				detail.get().setEmployeeStatus(dto.getEmployeeStatus());
				employeeDetailsRepo.save(detail.get());
				log.info(CHANGING_STATUS_SUCCESFULLY);
				statusRepo.save(changeStatus);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
