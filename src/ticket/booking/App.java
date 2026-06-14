package ticket.booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

import ticket.booking.entites.Train;
import ticket.booking.entites.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.util.UserServiceUtil;

public class App {
	public static void main(String[]args) {
		
		System.out.println("Running Train Booking System");
		Scanner scanner = new Scanner(System.in);
		int option =0;
		UserBookingService userBookingService;
		try {
			userBookingService = new UserBookingService();
			
		}catch(IOException ex) {
			ex.printStackTrace();
			System.out.println("Somethong wrong occured");
			return;
		}
		Train trainSelectedForBooking = new Train();
		String sourceSelected = "";
		String destSelected = "";
		while(option != 7) {
			System.out.println("Choose Option:");
			System.out.println("1. Sign Up");
			System.out.println("2. Login");
			System.out.println("3. Fetch Bookings");
			System.out.println("4. Search Trains");
			System.out.println("5. Book a seat");
			System.out.println("6. Cancel my Booking");
			System.out.println("7. Exit the App");
			option = scanner.nextInt();
			scanner.nextLine();
			if(option == 7) {
				System.out.println("Exiting the app, ThankYou for visiting!");
				break;
			}
			
			
			switch(option){
			case 1:
				System.out.println("Enter the Username to signup");
				String nameToSignUp = scanner.next();
				System.out.println("Enter the Password to signup");
				String passwordToSignUp = scanner.next();
				User userToSignUp = new User(nameToSignUp, passwordToSignUp, UserServiceUtil.hashPassword(passwordToSignUp), 
						new ArrayList<>(),UUID.randomUUID().toString());
				userBookingService.signUp(userToSignUp);
				System.out.println("New_User Created !!");
				break;
			case 2:
				System.out.println("Enter the Username to Login");
				String nameToLogin = scanner.next();
				System.out.println("Enter the Password to Login");
				String passwordToLogin = scanner.next();
				User userToLogin = new User(nameToLogin, passwordToLogin, UserServiceUtil.hashPassword(passwordToLogin), 
						new ArrayList<>(),UUID.randomUUID().toString());
				try {
				userBookingService = new UserBookingService(userToLogin);
				System.out.println("Login successfull");
				}
				catch(IOException ex) {
					return;
				}
				break;
				
			case 3:
				System.out.println("Fetching your bookings");
				userBookingService.fetchBookings();
			    break;
			    
			case 4:
				System.out.println("Search Trains on the basis of Source --> destination of user");
				System.out.print("Enter Source -->");
				String source = scanner.next();
				System.out.println();
				System.out.print("Enter Destination -->");
				String dest = scanner.next();
				sourceSelected = source;
				destSelected = dest;
				List<Train>trains = userBookingService.getTrains(source,dest);
				int index =1;
				for(Train t : trains) {
					System.out.println(index + " Train id :"+ t.getTrainId());
					
					for(Map.Entry<String, String> entry: t.getStationTimes().entrySet()) {
						System.out.println("station "+entry.getKey()+" time: "+entry.getValue());
					}
					index++;
				}
				 System.out.println("Select a train by typing 1,2,3...");
                 trainSelectedForBooking = trains.get(scanner.nextInt()-1);
                 System.out.println("DEBUG: " + trainSelectedForBooking.getSeats());
                 break;
			case 5:
				 System.out.println("Select a seat out of these seats");
                 List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);
                 for (List<Integer> row: seats){
                     for (Integer val: row){
                         System.out.print(val+" ");
                     }
                     System.out.println();
                 }
                 System.out.println("Select the seat by typing the row and column");
                 System.out.println("Enter the row");
                 int row = scanner.nextInt();
                 System.out.println("Enter the column");
                 int col = scanner.nextInt();
                 System.out.println("Booking your seat....");
                 Boolean booked = userBookingService.bookTrainSeat(trainSelectedForBooking, row, col, sourceSelected, destSelected);
                 if(booked.equals(Boolean.TRUE)){
                     System.out.println("Booked! Enjoy your journey");
                 }else{
                     System.out.println("Can't book this seat");
                 }
                 break;
			case 6:
				System.out.println("Enter the ticket Id to cancel");
				String ticketId = scanner.next();
				try {
					Boolean cancelled = userBookingService.cancelBooking(ticketId);
					if(cancelled) {
						System.out.println("Booking Cancelled Successfully !!");
						
					}
					else {
						System.out.println("No Booking found with that id");
					}
				}
				catch(IOException e) {
					System.out.println("Error cancelling booking !!");
				}
				break;
				
			
				
             default:
                 break;
			
			}
			 
			
				
			
			
		}
		
		
		//List<Integer>l = Arrays.asList(1,2,3,4,5,6,7,8,9);
		//List<Integer>l1 = l.stream().filter(i -> i%2==0).collect(Collectors.toList());
		//l.stream().map(e->e*2).collect(Collectors.toList());
	}
	/*e->e*2 lamda meaning
	 * Integer apply(Integer i){
	 * return 2*i;
	    } */
	
	
	

}
