import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.Person;
import java.io.IOException;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;

public class Main {
	public static void main(String[] args) {
		useCase1();
		try {
			useCase2();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void useCase1() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Jackson2HalModule());
		JavaType javaType = objectMapper.getTypeFactory().constructType(Resources.class);
		if (objectMapper.canDeserialize(javaType, null)) {
			System.out.println("True: can Deserialize");
		} else {
			System.out.println("False: can not Deserialize");
		}
	}

	private static void useCase2() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.registerModule(new Jackson2HalModule());

		String jsonString = getJsonString();

		Resources<Resource<Person>> resources = objectMapper.readValue(jsonString,
				objectMapper.getTypeFactory().constructParametricType(Resources.class,
						objectMapper.getTypeFactory().constructParametricType(Resource.class, Person.class)));

		if (resources.getContent().isEmpty() || resources.getLinks().isEmpty()) {
			System.out.println("Could not deserialize");
		} else {
			System.out.println("Done with deserialization");
		}
	}

	private static String getJsonString() {
		return "{\n"
			   + "    \"_embedded\": {\n"
			   + "        \"persons\": [\n"
			   + "            {\n"
			   + "                \"firstName\": \"test1\",\n"
			   + "                \"lastName\": \"test1\",\n"
			   + "                \"address\": [\n"
			   + "                    {\n"
			   + "                        \"addLine1\": \"line1\",\n"
			   + "                        \"addLine2\": \"line2\",\n"
			   + "                        \"zipcode\": \"12345\"\n"
			   + "                    },\n"
			   + "                    {\n"
			   + "                        \"addLine1\": \"line3\",\n"
			   + "                        \"addLine2\": \"line4\",\n"
			   + "                        \"zipcode\": \"12345\"\n"
			   + "                    }\n"
			   + "                ],\n"
			   + "                \"_links\": {\n"
			   + "                    \"self\": {\n"
			   + "                        \"href\": \"http://localhost:8080/persons/1\"\n"
			   + "                    },\n"
			   + "                    \"person\": {\n"
			   + "                        \"href\": \"http://localhost:8080/persons/1\"\n"
			   + "                    }\n"
			   + "                }\n"
			   + "            },\n"
			   + "            {\n"
			   + "                \"firstName\": \"test2\",\n"
			   + "                \"lastName\": \"test3\",\n"
			   + "                \"address\": [],\n"
			   + "                \"_links\": {\n"
			   + "                    \"self\": {\n"
			   + "                        \"href\": \"http://localhost:8080/persons/2\"\n"
			   + "                    },\n"
			   + "                    \"person\": {\n"
			   + "                        \"href\": \"http://localhost:8080/persons/2\"\n"
			   + "                    }\n"
			   + "                }\n"
			   + "            }\n"
			   + "        ]\n"
			   + "    },\n"
			   + "    \"_links\": {\n"
			   + "        \"self\": {\n"
			   + "            \"href\": \"http://localhost:8080/persons{?page,size,sort}\",\n"
			   + "            \"templated\": true\n"
			   + "        },\n"
			   + "        \"profile\": {\n"
			   + "            \"href\": \"http://localhost:8080/profile/persons\"\n"
			   + "        }\n"
			   + "    },\n"
			   + "    \"page\": {\n"
			   + "        \"size\": 20,\n"
			   + "        \"totalElements\": 2,\n"
			   + "        \"totalPages\": 1,\n"
			   + "        \"number\": 0\n"
			   + "    }\n"
			   + "}";
	}
}
