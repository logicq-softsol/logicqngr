package com.logicq.ngr.simulator.cashflow;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Simulator {
	static ObjectMapper mapper = new ObjectMapper();
	static Map<String, String> compdetails = new HashMap<>();
	static Random ran = new Random();

	public static void main(String[] args) throws Exception {
		ProducerKafka kfakProducer = new ProducerKafka();
		compdetails.put("logicq#0", "Pune");
		compdetails.put("logicq#1", "Mumbai");
		compdetails.put("logicq#2", "Banglore");
		compdetails.put("logicq#3", "Kolkata");
		compdetails.put("logicq#4", "Odisha");
		compdetails.put("logicq#5", "Hydrabad");

		Map<String, Object> data = new HashMap<>();
		setupCompanyNameAndLocation(data);
		String generatedData = mapper.writeValueAsString(data);
		kfakProducer.sendTokafka(generatedData);
	}

	static void setupCompanyNameAndLocation(Map<String, Object> data) {
		int randomNum = ran.nextInt((5 - 0) + 1) + 0;
		String companyName = "logicq#" + randomNum;
		String location = compdetails.get(companyName);
		data.put("companyname", companyName);
		data.put("location", location);
		setupInflowBalance(data);
		setupOutFlowBalance(data);
	}

	static void setupInflowBalance(Map<String, Object> data) {
		double totalflow = 0d;
		double openingBal = 10000 + (9999999 - 10000) * ran.nextDouble();
		data.put("OpeningBal", openingBal);
		totalflow = totalflow + openingBal;

		double sharecapital = 100000 + (9999999 - 100000) * ran.nextDouble();
		data.put("ShareCapital-ODT", sharecapital);
		totalflow = totalflow + sharecapital;

		double loansfrmDirectors = 1000000 + (999999999 - 1000000) * ran.nextDouble();
		data.put("LoansfrmDirectors", loansfrmDirectors);
		totalflow = totalflow + loansfrmDirectors;

		double consultancy = 1000 + (999999 - 1000) * ran.nextDouble();
		data.put("Consultancy", consultancy);
		totalflow = totalflow + consultancy;

		double interestonFDR = 10 + (9999 - 10) * ran.nextDouble();
		data.put("InterestonFDR", interestonFDR);
		totalflow = totalflow + interestonFDR;

		double others = 1000000 + (9999999 - 1000000) * ran.nextDouble();
		data.put("Others", others);
		totalflow = totalflow + others;

		data.put("TotalInflow", totalflow);
	}

	static void setupOutFlowBalance(Map<String, Object> data) {

		double outflow = 0d;
		double rentRD = 10000 + (9999999 - 10000) * ran.nextDouble();
		data.put("Rent-R&D", rentRD);
		outflow = outflow + rentRD;

		double rentHO = 1000 + (9999 - 1000) * ran.nextDouble();
		data.put("Rent-HO", rentHO);
		outflow = outflow + rentHO;

		double hoCapex = 1000 + (999999 - 1000000) * ran.nextDouble();
		data.put("HO-Capex", hoCapex);
		outflow = outflow + hoCapex;

		double rdCapex = 1000 + (999999 - 1000) * ran.nextDouble();
		data.put("R&DCapex", rdCapex);
		outflow = outflow + rdCapex;

		double rdChemicals = 1000 + (999999 - 10000) * ran.nextDouble();
		data.put("R&DChemicals", rdChemicals);
		outflow = outflow + rdChemicals;

		double rdConsumable = 100 + (9999 - 100) * ran.nextDouble();
		data.put("R&DConsumable", rdConsumable);
		outflow = outflow + rdConsumable;

		double rdGlassware = 100 + (9999 - 100) * ran.nextDouble();
		data.put("R&DGlassware", rdGlassware);
		outflow = outflow + rdGlassware;

		double rdSpares = 1000 + (9999 - 1000) * ran.nextDouble();
		data.put("R&DSpares", rdSpares);
		outflow = outflow + rdSpares;

		double repairsMaint = 100 + (9999 - 100) * ran.nextDouble();
		data.put("Repairs&Maint", repairsMaint);
		outflow = outflow + repairsMaint;

		double officeMaint = 100 + (9999 - 100) * ran.nextDouble();
		data.put("OfficeMaint", officeMaint);
		outflow = outflow + officeMaint;

		double analysisChgs = 10000 + (999999 - 10000) * ran.nextDouble();
		data.put("AnalysisChgs", analysisChgs);
		outflow = outflow + analysisChgs;

		double expsreimbRD = 100 + (9999 - 100) * ran.nextDouble();
		data.put("ExpsreimbR&D", expsreimbRD);
		outflow = outflow + expsreimbRD;

		double rdImprest = 10000 + (999900 - 10000) * ran.nextDouble();
		data.put("R&DImprest", rdImprest);
		outflow = outflow + rdImprest;

		double hoImprest = 100 + (9999 - 100) * ran.nextDouble();
		data.put("HOImprest", hoImprest);
		outflow = outflow + hoImprest;

		double investmentFDR = 300000 + (999999 - 300000) * ran.nextDouble();
		data.put("Investment-FDR", investmentFDR);
		outflow = outflow + investmentFDR;

		double telephone = 1000 + (99999 - 1000) * ran.nextDouble();
		data.put("Telephone", telephone);
		outflow = outflow + telephone;

		double professionalcharges = 10000 + (999900 - 10000) * ran.nextDouble();
		data.put("Professionalcharges", professionalcharges);
		outflow = outflow + rdConsumable;

		double licRegnchgs = 45000 + (99999 - 45000) * ran.nextDouble();
		data.put("Lic&Regnchgs", licRegnchgs);
		outflow = outflow + licRegnchgs;

		double legalchgsROCfilingfees = 100 + (9999 - 100) * ran.nextDouble();
		data.put("Legalchgs-ROCfilingfees", legalchgsROCfilingfees);
		outflow = outflow + legalchgsROCfilingfees;

		double domTravel = 100 + (9999 - 100) * ran.nextDouble();
		data.put("Dom-Travel", domTravel);
		outflow = outflow + domTravel;

		double insuranceTravelForeign = 10000 + (99999 - 10000) * ran.nextDouble();
		data.put("Insurance-TravelForeign", insuranceTravelForeign);
		outflow = outflow + rdConsumable;

		double printingStationery = 100 + (9999 - 100) * ran.nextDouble();
		data.put("Printing/Stationery", printingStationery);
		outflow = outflow + rdConsumable;

		double salary = 10000 + (999999 - 10000) * ran.nextDouble();
		data.put("Salary", salary);
		outflow = outflow + salary;

		double bankchgs = 100 + (9999 - 100) * ran.nextDouble();
		data.put("Bankchgs", bankchgs);
		outflow = outflow + bankchgs;

		double iciciODInterest = 10000 + (999999 - 10000) * ran.nextDouble();
		data.put("ICICI-ODInterest", iciciODInterest);
		outflow = outflow + iciciODInterest;

		double oDTLoan = 1000000 + (9999999 - 1000000) * ran.nextDouble();
		data.put("ODTLoan", oDTLoan);
		outflow = outflow + oDTLoan;

		double depositforPremises = 100 + (9999 - 100) * ran.nextDouble();
		data.put("DepositforPremises", depositforPremises);
		outflow = outflow + depositforPremises;

		double consultancychgs = 1000000 + (9999999 - 1000000) * ran.nextDouble();
		data.put("Consultancychgs", consultancychgs);
		outflow = outflow + consultancychgs;

		double tDSonRent194I = 100 + (9999 - 100) * ran.nextDouble();
		data.put("TDSonRent-194I", tDSonRent194I);
		outflow = outflow + tDSonRent194I;

		double tDSonProfessional194J = 100 + (9999 - 100) * ran.nextDouble();
		data.put("TDSonProfessional-194J", tDSonProfessional194J);
		outflow = outflow + tDSonProfessional194J;

		double cstdeposit = 10000 + (999999 - 10000) * ran.nextDouble();
		data.put("CSTdeposit", cstdeposit);
		outflow = outflow + cstdeposit;

		double gstVatdeposit = 10000 + (999999 - 10000) * ran.nextDouble();
		data.put("GSTVatdeposit", gstVatdeposit);
		outflow = outflow + gstVatdeposit;

		double tdsonLabourchgs = 10000 + (999999 - 10000) * ran.nextDouble();
		data.put("TDSonLabourchgs", tdsonLabourchgs);
		outflow = outflow + tdsonLabourchgs;

		data.put("TotalOutflow", outflow);
		Double inflow = (Double) data.get("TotalInflow");
		double closingbalance = outflow - inflow;
		data.put("ClosingBalance", inflow);

	}

}
