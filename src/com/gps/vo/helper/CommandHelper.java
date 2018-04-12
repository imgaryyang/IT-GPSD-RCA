package com.gps.vo.helper;

import com.gps.vo.Contract;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper {
	
	private List<Contract> contracts = new ArrayList<Contract>();

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

}
