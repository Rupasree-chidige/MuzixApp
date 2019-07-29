package com.stackroute.muzix.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.stackroute.muzix.model.Track;
import com.stackroute.muzix.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImpl implements TrackService {

	TrackRepository trackRepository;

	@Autowired
	public TrackServiceImpl(TrackRepository trackRepository) {
		this.trackRepository = trackRepository;
	}

	@Override
	public boolean saveTrack(Track track) {
			trackRepository.save(track);
			return true;
	}

	@Override
	public boolean deleteTrack(int id) {
			trackRepository.deleteById(id);
			return true;
	}

	@Override
	public List<Track> getAllTracks() {
		List<Track> trackList = trackRepository.findAll();
		return trackList;
	}

	@Override
	public boolean updateTrack(int id, Track track) {
			if(trackRepository.existsById(id) == true){
				trackRepository.save(track);
				return true;
			}
		return false;
	}

	@Override
	public void getTopTracks() {
//		Api uri
		final String uri = "http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=disco&api_key=4326c000f7df7c81681da5052adc2cf3&format=json";
//	    instantiation
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

//		get a json object as a String
		String json = restTemplate.getForObject(uri, String.class);

		try {
//			converting string as a json node
			JsonNode rootNode = mapper.readTree(json);
			ArrayNode arrayNode =  (ArrayNode)rootNode.path("tracks").path("track");
			//iterate the JSON array
			for (int i = 0; i < arrayNode.size(); i++) {
				//get a new Track object and fill it with data using setters
				Track track = new Track();
				track.setName(arrayNode.get(i).path("name").asText());
				track.setComment(arrayNode.get(i).path("artist").path("name").asText());
				//save the track to database
				trackRepository.save(track);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

  @Override
  public Optional<Track> getTrackById(int id) {
    Optional<Track> track = null;
	  if(trackRepository.existsById(id))
    {
     track=trackRepository.findById(id);
    }
    return track;
  }
}


