package com.developer.experiment2;

import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.lang.System;
import java.io.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

class EmployeeList{
	private ArrayList employeeNames = new ArrayList<String>();
	private ArrayList employeeIDs = new ArrayList<Integer>();
	int count = 0;
	public String[][] createEmployeeList() {
		Scanner sc = new Scanner(System.in);
		boolean ans = true;
		while(ans != false) {
			System.out.println("Enter employee name");
			count++;
			employeeNames.add(sc.nextLine());
			employeeIDs.add(count);
			System.out.println("Do you want to add another employee? yes or no");
			if(sc.nextLine().equals("no")) {
				ans = false;
			}
		}
		
		String[][]fullEmployeeList = new String[employeeNames.size()][employeeIDs.size()];
		for(int i = 0; i < employeeIDs.size(); i++) {
			fullEmployeeList[i][i] = (String) employeeNames.get(i);
		}
		
		return fullEmployeeList;
	}
	
	
}
@Controller
public class HomeController{
	@RequestMapping(value="/", produces = "application/json")
	@ResponseBody
	Object home() {
		return new EmployeeList();
	}
}
