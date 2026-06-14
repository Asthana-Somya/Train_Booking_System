package ticket.booking.entites;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;





@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String name;
	private String password;
	@JsonProperty("hash_password")
	private String hashPassword;
	@JsonProperty("tickets_booked")
	private List<Ticket>ticketsBooked;
	@JsonProperty("user_id")
	private String userId;
	//constructor
	public User(String name, String password, String hashPassword, List<Ticket>ticketsBooked,String userId ) {
		this.name =name;
		this.password =password;
		this.hashPassword =hashPassword;
		this.ticketsBooked =ticketsBooked;
		this.userId =userId;
	}
	//default constructor
	public User() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getHashPassword() {
		return hashPassword;
	}
	
	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}
	
	public List<Ticket> getTicketsBooked() {
		return ticketsBooked;
	}
	public void setTicketsBooked(List<Ticket> ticketsBooked) {
		this.ticketsBooked = ticketsBooked;
	}
	public void printTickets() {
		if(ticketsBooked != null && ticketsBooked.size()>0) {
			for(int i=0;i<ticketsBooked.size();i++) {
				System.out.println(ticketsBooked.get(i).getTicketInfo());
			}	
		}
		else {
			System.out.println("No Upcoming Journey Booked !!");
		}
		
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	
	
	
	

}
