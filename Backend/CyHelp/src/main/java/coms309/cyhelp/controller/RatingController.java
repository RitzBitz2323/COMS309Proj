package coms309.cyhelp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Rating;
import coms309.cyhelp.repository.RatingRepository;
import coms309.cyhelp.service.ActorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "RatingController", description = "Rest Controller for the Rating Entity.")
@RestController
public class RatingController {
	@Autowired
	RatingRepository ratingRepository;
	
	
}
