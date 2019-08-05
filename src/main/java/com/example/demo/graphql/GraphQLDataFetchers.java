package com.example.demo.graphql;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author zengxc
 * @since 2019/7/24
 */
@Component
public class GraphQLDataFetchers {

	private static List<Map<String, String>> books = Arrays.asList(
			ImmutableMap.of("id", "book-1",
					"name", "Harry Potter and the Philosopher's Stone",
					"pageCount", "223",
					"authorId", "author-1"),
			ImmutableMap.of("id", "book-2",
					"name", "Moby Dick",
					"pageCount", "635",
					"authorId", "author-2"),
			ImmutableMap.of("id", "book-3",
					"name", "Interview with the vampire",
					"pageCount", "371",
					"authorId", "author-3")
	);

	private static List<Map<String, String>> naturalPersons = Arrays.asList(
			ImmutableMap.of("name", "张三",
					"mobileNumber", "13800138000",
					"identityCard", "441721199001012031","age","18"),

			ImmutableMap.of("name", "李四",
					"mobileNumber", "13800138001",
					"identityCard", "441721199001012032"),
			ImmutableMap.of("name", "王五",
					"mobileNumber", "13800138002",
					"identityCard", "441721199001012033")
	);

	private static List<Map<String, String>> authors = Arrays.asList(
			ImmutableMap.of("id", "author-1",
					"firstName", "Joanne",
					"lastName", "Rowling"),
			ImmutableMap.of("id", "author-2",
					"firstName", "Herman",
					"lastName", "Melville"),
			ImmutableMap.of("id", "author-3",
					"firstName", "Anne",
					"lastName", "Rice")
	);

	public DataFetcher getBookByIdDataFetcher() {
		return dataFetchingEnvironment -> {
			String bookId = dataFetchingEnvironment.getArgument("id");
			return books
					.stream()
					.filter(book -> book.get("id").equals(bookId))
					.findFirst()
					.orElse(null);
		};
	}

	public DataFetcher getNaturalPersonByIdDataFetcher() {
		return dataFetchingEnvironment -> {
			String id = dataFetchingEnvironment.getSource();
			Map<String, String> person = getNaturalPersonByIdentityCard(id);
			return person == null ? getNaturalPersonByMobileNumber(id) : person;
		};
	}

	private Map<String, String> getNaturalPersonByMobileNumber(String mobileNumber) {
		return naturalPersons
				.stream()
				.filter(book -> book.get("mobileNumber").equals(mobileNumber))
				.findFirst()
				.orElse(null);
	}

	private Map<String, String> getNaturalPersonByIdentityCard(String identityCard) {
		return naturalPersons
				.stream()
				.filter(book -> book.get("identityCard").equals(identityCard))
				.findFirst()
				.orElse(null);
	}

	private Map<String, String> getNaturalPersonById(String mobileNumber) {
		return naturalPersons
				.stream()
				.filter(book -> book.get("mobileNumber").equals(mobileNumber))
				.findFirst()
				.orElse(null);
	}

	public DataFetcher getAuthorDataFetcher() {
		return dataFetchingEnvironment -> {
			Map<String, String> book = dataFetchingEnvironment.getSource();
			String authorId = book.get("authorId");
			return authors
					.stream()
					.filter(author -> author.get("id").equals(authorId))
					.findFirst()
					.orElse(null);
		};
	}
}
