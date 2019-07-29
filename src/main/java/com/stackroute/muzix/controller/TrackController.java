package com.stackroute.muzix.controller;

import com.stackroute.muzix.model.Track;
import com.stackroute.muzix.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class TrackController {

	private TrackService trackService;

	@Autowired
	public TrackController(TrackService trackService) {
		this.trackService = trackService;
	}

	@GetMapping("tracks")
	public ResponseEntity<?> getAllTracks(){
		ResponseEntity responseEntity;
		List<Track> tracks;
		try{
			tracks = trackService.getAllTracks();
			responseEntity = new ResponseEntity<List<Track>>(tracks, HttpStatus.OK);
		} catch (Exception e){
			responseEntity = new ResponseEntity<String>("Exception", HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}

	@PostMapping("track")
	public ResponseEntity<?> savetrack(@RequestBody Track track){
		ResponseEntity responseEntity;
		try{
			trackService.saveTrack(track);
			responseEntity = new ResponseEntity<String>("Successfully Created", HttpStatus.CREATED);
		} catch (Exception e){
			responseEntity = new ResponseEntity<String>("Exception", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

	@PutMapping(value = "track/{id}")
	public ResponseEntity<?> updateTrack(@PathVariable int id,@RequestBody Track track){
		ResponseEntity responseEntity;
		try{
			trackService.updateTrack(id,track);
			responseEntity = new ResponseEntity<String>("Successfully Updated", HttpStatus.OK);
		} catch (Exception e){
			responseEntity = new ResponseEntity<String>("Exception", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

	@DeleteMapping("track/{id}")
	public ResponseEntity<?> deleteTrack(@PathVariable("id") int id){
		ResponseEntity responseEntity;
		try{
			trackService.deleteTrack(id);
			responseEntity = new ResponseEntity<String>("Successfully Deleted", HttpStatus.OK);
		} catch (Exception e){
			responseEntity = new ResponseEntity<String>("Exception", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

	@GetMapping("toptracks")
	public ResponseEntity<?> getTopTracks(){
		ResponseEntity responseEntity;
		List<Track> tracks;
		try{
			trackService.getTopTracks();
			responseEntity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e){
			responseEntity = new ResponseEntity<String>("Exception", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
  @GetMapping("gettrack/{id}")
  public ResponseEntity<?> getTopTracks(@PathVariable("id") int id){
    ResponseEntity responseEntity;

    try{
     Optional<Track> track= trackService.getTrackById(id);
      responseEntity = new ResponseEntity<String>(String.valueOf(track), HttpStatus.OK);
    } catch (Exception e){
      responseEntity = new ResponseEntity<String>("Exception", HttpStatus.CONFLICT);
    }
    return responseEntity;
  }
}
