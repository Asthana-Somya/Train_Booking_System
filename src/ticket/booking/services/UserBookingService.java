package ticket.booking.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ticket.booking.entites.Ticket;
import ticket.booking.entites.Train;

import ticket.booking.entites.User;
import ticket.booking.util.UserServiceUtil;

public class UserBookingService {
 //constructor for this claass
	private User user;
	private List<User>userList;
	//mapping  file of user.json to user entity
	private ObjectMapper objectMapper = new ObjectMapper();
	///ticketBooking/src/localDb/users.json  ---recheck the path 
	private static final String USERS_PATH ="src/localDb/users.json";
	
	public UserBookingService() throws  IOException {
		this.userList=loadUsers();
	}
	public UserBookingService(User user) throws  IOException {
		this.user = user;
		this.userList = loadUsers();
		
	}
	
	public List<User> loadUsers() throws IOException{
		File users = new File(USERS_PATH);
		if(!users.exists() || users.length() == 0) {
			return new ArrayList<>();
		}
		return objectMapper.readValue(users,new TypeReference<List<User>>(){});
		
	}
	
	//loginUser
	public Boolean loginUser() {
		//optional for npe
		Optional<User>foundUser = userList.stream().filter(user1 ->{
			return user1.getName().equalsIgnoreCase(user.getName())&& UserServiceUtil.checkPassword(user.getPassword(),user1.getHashPassword());
        }).findFirst();
		return foundUser.isPresent();
	}
	//signup User
	public Boolean signUp(User user1) {
		try {
			userList.add(user1);
			saveUserListToFile();
			return Boolean.TRUE;
		}
		catch(IOException ex) {
			return Boolean.FALSE;
		}
	}

	private void saveUserListToFile() throws  IOException {
		// TODO Auto-generated method stub
		
		File userFile = new File(USERS_PATH);
		objectMapper.writeValue(userFile, userList);
		
	}
	//json--> Object(User) == Desieralize
	//Object(User)-->Json == Serialize
	
	
	//complete this method
	public Boolean cancelBooking(String ticketId) throws IOException {
		//fetch ticket through ticket id ( from ticketBooked)
		 for(User user : userList) {

		        boolean removed = user.getTicketsBooked()
		                              .removeIf(ticket ->
		                                   ticket.getTicketId().equals(ticketId));

		        if(removed) {
		            saveUserListToFile();
		            return true;
		        }
		    }

		
		//then remove it from ticket booked
		//using stream.filter(remove ticketid)
		
		// then saveUserListToFile() 
		return Boolean.FALSE;
		
	}
	public void fetchBookings(){
		Optional<User> userFetched = userList.stream().filter(user1 -> {
	        System.out.println("Comparing: " + user1.getName() + " vs " + user.getName());
	        System.out.println("Password match: " + UserServiceUtil.checkPassword(user.getPassword(), user1.getHashPassword()));
	        return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashPassword());
	    }).findFirst();
	    if(userFetched.isPresent()){
	        userFetched.get().printTickets();
	    }
    }
	
	public List<Train>getTrains(String source, String destination){
		try {
			TrainService trainService = new TrainService();
			return trainService.searchTrains(source,destination);
		}
		catch(Exception e) {
			return null;
		}
		
	}
	 public List<List<Integer>> fetchSeats(Train train){
         return train.getSeats();
    }
	 
	 public Boolean bookTrainSeat(Train train, int row, int seat, String source, String destination) {
	        try{
	            TrainService trainService = new TrainService();
	            List<List<Integer>> seats = train.getSeats();
	            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
	                if (seats.get(row).get(seat) == 0) {
	                    seats.get(row).set(seat, 1);
	                    train.setSeats(seats);
	                    trainService.addTrain(train);
	                    Ticket ticket = new Ticket(
	                    	    UUID.randomUUID().toString(),
	                    	    user.getUserId(),
	                    	    source,
	                    	    destination,
	                    	    new Date(),
	                    	    train
	                    	);
	                    userList.stream()
	                    .filter(u -> u.getName().equals(user.getName()))
	                    .findFirst()
	                    .ifPresent(u -> u.getTicketsBooked().add(ticket));
	                user.getTicketsBooked().add(ticket);
	                saveUserListToFile();
	                    	return true; // Booking successful
	                } else {
	                    return false; // Seat is already booked
	                }
	            } else {
	                return false; // Invalid row or seat index
	            }
	        }catch (IOException ex){
	            return Boolean.FALSE;
	        }
	    }
	
	
	
	
	
}
