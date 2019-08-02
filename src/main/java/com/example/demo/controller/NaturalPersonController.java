package com.example.demo.controller;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zengxc
 * @since 2019/7/24
 */
@RestController
public class NaturalPersonController {

	@Autowired
	GraphQL graphQL;

	@GetMapping("/naturalPerson/{id}")
	public Object getNaturalPerson(@PathVariable("id") String id) {
		String query ="query NaturalPerson($id:String){" +
				" naturalPerson(id: $id) {" +
				"    name,mobileNumber" +
				"  }" +
				"}";
		Map<String, Object> variables = new LinkedHashMap<>();
		variables.put("id",id);
		ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput()
				.query(query)
				.variables(variables)
				.build());
		Map<String, String> data = executionResult.getData();
		return data.get("naturalPerson");

	}
}
