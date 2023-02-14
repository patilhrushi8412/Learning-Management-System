package com.te.lmsproject.employeeservices;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.te.lmsproject.employeedto.EmployeeDto;
import com.te.lmsproject.employeeentity.Employee;
import com.te.lmsproject.employeerepository.EmployeeRepository;
import com.te.lmsproject.lmscustomexception.CustomException;
import com.te.lmsproject.lmsresponce.PageResponce;
import static com.te.lmsproject.lmsconstants.Constants.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeServices {

	@Autowired
	EmployeeRepository repo;

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public Employee addEmployee(@Valid EmployeeDto employeeDto) {
		try {
			Optional<Employee> emp = repo.findByEmail(employeeDto.getEmail());
			if (emp.isPresent()) {
				log.error(EMPLOYEE_ALLREADY_PRESENT_ON_THIS_EMAIL_ID, emp);
				throw new CustomException(EMPLOYEE_ALLREADY_PRESENT_ON_THIS_EMAIL_ID);
			} else {
				if (employeeDto == null) {
					log.error(SOMETHING_WENT_WRONG_TO_ADD_EMPLOYEE);
					throw new CustomException(SOMETHING_WENT_WRONG_TO_ADD_EMPLOYEE);
				} else {
					Employee employee = new Employee();
					BeanUtils.copyProperties(employeeDto, employee);
					employee.setEmployeeId(
							employeeDto.getEmployeeId() + "022" + String.format("%03d", repo.findAll().size() + 1));
					Employee save = repo.save(employee);
					log.info(EMPLOYEE_SAVE_SUCCESFULLY);
					return save;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public Employee getEmployeeDetails(@Valid int id) {
		try {
			Optional<Employee> findById = repo.findById(id);
			if (!findById.isPresent()) {
				log.error(EMPLOYEE_DETAILS_NOT_PRESENT_ON_THIS_ID);
				throw new CustomException(EMPLOYEE_DETAILS_NOT_PRESENT_ON_THIS_ID);
			} else {
				log.info(GETTING_EMPLOYEE_DETAILS_SUCCESFULLY);
				return findById.get();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public Employee updateEmployee(@Valid EmployeeDto employeeDto) {
		try {
			log.info(UPDATING_EMPLOYEE_DETAILS);
			Optional<Employee> emp = repo.findByEmail(employeeDto.getEmail());
			if (!emp.isPresent()) {
				log.error(EMPLOYEE_NOT_PRESENT_WHICH_YOU_WANT_TO_UPDATE);
				throw new CustomException(EMPLOYEE_NOT_PRESENT_WHICH_YOU_WANT_TO_UPDATE);
			} else {
				String id = emp.get().getEmployeeId();
				BeanUtils.copyProperties(employeeDto, emp.get());
				emp.get().setEmployeeId(id);
				log.info(EMPLOYEE_UPDATED_SUCCESSFULLY);
				return repo.save(emp.get());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public PageResponce getAllEmployeeDetails(int pageNumber, int pageSize, String str) {
		try {
			log.info(GETTING_ALL_EMPLOYEE_DETAILS);
			Pageable p = PageRequest.of(pageNumber, pageSize);
			Page<Employee> pages = repo
					.findAllByEmployeeIdContainingIgnoreCaseOrEmployeeNameContainingIgnoreCaseOrDateOfJoiningContainingIgnoreCaseOrDateOfBirthContainingIgnoreCaseOrEmailContainingIgnoreCaseOrBloodGroupContainingIgnoreCase(
							str, str, str, str, str, str, p);
			List<Employee> content = pages.getContent();
			if (content.isEmpty()) {
				log.error(NO_ANY_EMPLOYEE_PRESENT_AT_THE_MOMENT);
				throw new CustomException(NO_ANY_EMPLOYEE_PRESENT_AT_THE_MOMENT);
			} else {
				PageResponce responce = new PageResponce();
				responce.setContent(Arrays.asList(content));
				responce.setLastPage(pages.isLast());
				responce.setTotalPages(pages.getTotalPages());
				responce.setPageNumber(pages.getNumber());
				responce.setPageSize(pages.getSize());
				responce.setTotalElements(pages.getTotalElements());
				log.info(GETTING_ALL_EMPLOYEE_DETAILS_SUCCESFULSLY);
				return responce;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public void deleteEmployee(@Valid String employeeId) {
		try {
			log.info(DELETING_EMPLOYEE_DETAIL, employeeId);
			Optional<Employee> findById = repo.findByEmployeeId(employeeId);
			if (!findById.isPresent()) {
				log.error(WHICH_EMPLOYEE_YOU_WANT_TO_DELETE_IS_NOT_PRESENT, employeeId);
				throw new CustomException(WHICH_EMPLOYEE_YOU_WANT_TO_DELETE_IS_NOT_PRESENT);
			} else {
				log.info(DELETING_EMPLOYEE_DETAILS_SUCCESFULLY);
				repo.delete(findById.get());
			}
		} catch (Exception e) {
			throw e;
		}
	}
}