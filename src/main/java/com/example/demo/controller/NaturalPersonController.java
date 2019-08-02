package com.example.demo.controller;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zengxc
 * @since 2019/7/24
 */
@Slf4j
@RestController
public class NaturalPersonController {

	@Autowired
	GraphQL graphQL;

	@GetMapping("/naturalPerson/{query}")
	public Object getNaturalPerson(@PathVariable("query") String query) {
		Map<String, String> data = graphQL.execute("{ naturalPerson(id: \"" + query + "\") {" +
				" name "+
				" mobileNumber" +
				" identityCard" +
				"}}").getData();
		return data.get("naturalPerson");

	}
	@RequestMapping(value = "/graphql", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object executeOperation(@RequestBody Map body) {
		String query = (String) body.get("query");
		Map<String, Object> variables = (Map<String, Object>) body.get("variables");
		if (variables == null) {
			variables = new LinkedHashMap<>();
		}
		ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput().query(query).variables(variables).build());
		Map<String, Object> result = new LinkedHashMap<>();
		if (executionResult.getErrors().size() > 0) {
			result.put("errors", executionResult.getErrors());
			log.error("Errors: {}", executionResult.getErrors());
		}
		result.put("data", executionResult.getData());
		return result;
	}
}
