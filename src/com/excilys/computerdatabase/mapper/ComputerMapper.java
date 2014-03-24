package com.excilys.computerdatabase.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.service.CompanyServiceImpl;
import com.excilys.computerdatabase.service.ComputerServiceImpl;

public class ComputerMapper {

	ComputerServiceImpl myComputerService = new ComputerServiceImpl();
	CompanyServiceImpl myCompanyService = new CompanyServiceImpl();

	public ComputerMapper() {

	}

	public Computer toComputer(ComputerDTO cDTO) {

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		Computer computer = null;

		try {
			computer = new Computer();
			computer.setId(cDTO.getId());
			computer.setName(cDTO.getName());
			if (cDTO.getIntroduced() != null) {
				computer.setIntroduced(sdf.parse(cDTO.getIntroduced()));
			}
			if (cDTO.getDiscontinued() != null) {
				computer.setDiscontinued(sdf.parse(cDTO.getDiscontinued()));
			}
			Company company = myCompanyService
					.retrieveById(cDTO.getCompanyId());
			computer.setCompany(company);

		} catch (ParseException e) {
			System.err.println("Erreur lors du parse des dates.");
			e.printStackTrace();
		}

		return computer;
	}

	public ComputerDTO toComputerDTO(Computer c) {

		ComputerDTO cDTO = new ComputerDTO();
		cDTO.setId(c.getId());
		cDTO.setName(c.getName());
		if (c.getIntroduced() != null) {
			cDTO.setIntroduced(c.getIntroduced().toString());
		}
		if (c.getDiscontinued() != null) {
			cDTO.setDiscontinued(c.getDiscontinued().toString());
		}
		cDTO.setCompanyId(c.getCompany().getId());

		return cDTO;
	}
}
