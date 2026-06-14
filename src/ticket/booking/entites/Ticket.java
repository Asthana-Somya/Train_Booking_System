package ticket.booking.entites;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ticket {
	private String ticketId;
	private String userId;
	private String source;
	private String destination;
	private Date dateOFTravel;
	private Train train;
	
	
	
	public Ticket(String ticketId, String userId, String source, String destination, Date dateOFTravel, Train train) {
		super();
		this.ticketId = ticketId;
		this.userId = userId;
		this.source = source;
		this.destination = destination;
		this.dateOFTravel = dateOFTravel;
		this.train = train;
	}
	public Ticket() {
		
	}
	@JsonIgnore
	public String getTicketInfo() {
		return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s",ticketId, userId ,source, destination, dateOFTravel);
	}
	
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Date getDateOFTravel() {
		return dateOFTravel;
	}
	public void setDateOFTravel(Date dateOFTravel) {
		this.dateOFTravel = dateOFTravel;
	}
	public Train getTrain() {
		return train;
	}
	public void setTrain(Train train) {
		this.train = train;
	}
	
	
	

}
