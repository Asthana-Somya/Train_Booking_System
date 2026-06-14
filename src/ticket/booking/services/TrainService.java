package ticket.booking.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ticket.booking.entites.Train;

public class TrainService {
	private List<Train>trainList;
	private ObjectMapper objectMapper = new ObjectMapper();
	private static final String TRAIN_PATH ="src/localDb/train.json";
	
	public TrainService() throws  IOException {
		File train = new File(TRAIN_PATH);
		 if (!train.exists() || train.length() == 0) {
		        trainList = new ArrayList<>();
		        return;
		    }
		trainList = objectMapper.readValue(train,new TypeReference<List<Train>>(){});
	}

	
	public List<Train> searchTrains(String source, String destination){
		return trainList.stream()
				.filter(train -> validTrain(train, source, destination))
				.collect(Collectors.toList());
	}
	public void addTrain(Train newTrain) throws IOException {
		Optional<Train> existingTrain = trainList.stream()
				.filter(train -> train.getTrainId().equalsIgnoreCase(newTrain.getTrainId()))
	            .findFirst();
		
		if(existingTrain.isPresent()) {
			updateTrain(newTrain);
		}
		else {
			trainList.add(newTrain);
			saveTrainListToFile();
		}
	}
	public void updateTrain(Train updatedTrain) {
		//find the index of the train with the same trainId
		OptionalInt index = IntStream.range(0, trainList.size()) 
				.filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId()))
				.findFirst();
	}
	private void saveTrainListToFile() throws  IOException {
		// TODO Auto-generated method stub
		
		File trainFile = new File(TRAIN_PATH);
		objectMapper.writeValue(trainFile, trainList);
		
	}

	private boolean validTrain(Train train, String source, String destination) {
		// TODO Auto-generated method stub
		List<String> stationOrder = train.getStations();
		int sourceIndex = stationOrder.indexOf(source.toLowerCase());
		int destinationIndex = stationOrder.indexOf(destination.toLowerCase());
		return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
		
	}
}
