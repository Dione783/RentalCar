package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	private Double pricePerHour;
	private Double pricePerDay;
	private TaxService texService;
	
	
	public RentalService(Double pricePerHour, Double pricePerDay,TaxService texService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.texService=texService;
	}

	public void processInvoice(CarRental rental) {
		long t1 = rental.getFinish().getTime();
		long t2 = rental.getStart().getTime();
		double hours = (double)(t1-t2) /1000 /60 /60;
		double basicPayment;
		if(hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;
		}else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		double tax = texService.tax(basicPayment);
		
		rental.setInvoice(new Invoice(basicPayment,tax));
	}
	
}
