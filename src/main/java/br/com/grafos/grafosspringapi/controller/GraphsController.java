package br.com.grafos.grafosspringapi.controller;

import java.io.InputStream;

import com.google.gson.JsonObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grafos.grafosspringapi.services.BuildGraphsTools;
import br.com.grafos.grafosspringapi.services.GraphsServices;
import br.com.grafos.grafosspringapi.services.InputServices;
@CrossOrigin
@RestController
@RequestMapping("/grafos")
public class GraphsController {
	private InputServices inputServices = new InputServices();
	private GraphsServices graphsServices = new GraphsServices();
	private BuildGraphsTools buildGraphsTools = new BuildGraphsTools();
	@CrossOrigin
	@GetMapping("/helloWorld")
	public String helloWorld() {
		return "hello world"; 
	}
	@CrossOrigin
	@PostMapping
	public ResponseEntity<String> createGraphs(InputStream data) {
		JsonObject body = this.inputServices.readInputStreamData(data);
		String id = body.get("id").getAsString();
		String type = buildGraphsTools.detectType(id);
		
		if (type.equals("empresa") || type.equals("pessoa")) {
			return new ResponseEntity<>(this.graphsServices.buildGraphs(body, type).toString(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Parâmetros inválidos", HttpStatus.BAD_REQUEST);
		}	
	}	
}
