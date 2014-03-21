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

		SimpleDateFormat parser = new SimpleDateFormat("YYYY-MM-dd");
		Computer computer = null;

		try {
			computer = new Computer();
			computer.setName(cDTO.getName());
			computer.setIntroduced(parser.parse(cDTO.getIntroduced()));

			computer.setDiscontinued(parser.parse(cDTO.getDiscontinued()));

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
		cDTO.setName(c.getName());
		cDTO.setIntroduced(c.getIntroduced().toString());
		cDTO.setDiscontinued(c.getDiscontinued().toString());
		cDTO.setCompanyId(c.getCompany().getId());

		return cDTO;
	}
}
