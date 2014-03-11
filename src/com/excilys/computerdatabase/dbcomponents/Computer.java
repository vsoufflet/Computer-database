package com.excilys.computerdatabase.dbcomponents;

import java.sql.Date;

public class Computer {

	int id;
	String name;
	Date introduced;
	Date discontinued;
	int company_id;
	
	public Computer(int id, String name, Date introduced, Date discontinued, int company_id) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}

	public Computer() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getIntroduced() {
		return introduced;
	}


	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}


	public Date getDiscontinued() {
		return discontinued;
	}


	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}


	public int getCompany_id() {
		return company_id;
	}


	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	
	@Override
	public String toString() {
		return "Computer Name: "+name+" + id: + "+id+" Introduced Date: "+introduced+
				" Discontinued Date: "+discontinued+" Company id: "+company_id;
	}

}
