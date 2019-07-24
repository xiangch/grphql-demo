package com.example.demo.controller;

import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zengxc
 * @since 2019/7/24
 */
@RestController
public class NaturalPersonController {

	@Autowired
	GraphQL graphQL;

	@GetMapping("/naturalPerson/{query}")
	public Object getNaturalPerson(@PathVariable("query") String query) {
		Map<String, String> data = graphQL.execute("{" +
				"  naturalPerson(id: \"" + query + "\") {" +
				"    name" +
				"  }" +
				"}").getData();
		return data.get("naturalPerson");

	}
}
