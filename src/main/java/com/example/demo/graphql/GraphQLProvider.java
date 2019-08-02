package com.example.demo.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * @author zengxc
 * @since 2019/7/24
 */
@Component
public class GraphQLProvider {

	private GraphQL graphQL;
	@Autowired
	GraphQLDataFetchers graphQLDataFetchers;

	@Bean
	public GraphQL graphQL() {
		return graphQL;
	}

	@PostConstruct
	public void init() throws IOException {
		SchemaParser schemaParser = new SchemaParser();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();

		// each registry is merged into the main registry
		typeRegistry.merge(schemaParser.parse(loadSchema("root")));
		typeRegistry.merge(schemaParser.parse(loadSchema("natural_person")));
		typeRegistry.merge(schemaParser.parse(loadSchema("legal_person")));


		GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, buildWiring());
		this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();

	}

	private File loadSchema(String schemaName) {
		ClassPathResource classPathResource = new ClassPathResource("graphql/" + schemaName + ".schema");
		File schemaFile = null;
		try (InputStream inputStream = classPathResource.getInputStream()) {
			//生成目标文件
			schemaFile = File.createTempFile(schemaName + "_template", ".schema");
			FileUtils.copyInputStreamToFile(inputStream, schemaFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return schemaFile;
	}


	private RuntimeWiring buildWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type(newTypeWiring("Query")
						.dataFetcher("naturalPerson", graphQLDataFetchers.getNaturalPersonByIdDataFetcher()
						).dataFetcher("legalPerson", graphQLDataFetchers.getNaturalPersonByIdDataFetcher()
						)).build();
	}
}
