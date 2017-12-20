package jupiterpa.gateway.security;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jupiterpa.gateway.security.model.JwtUserLogin;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void login() throws Exception {
		JwtUserLogin login = new JwtUserLogin("name","admin","42");
		MvcResult result = 
		mockMvc.perform(post("/token").content(toJson(login)).contentType(APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk())
		        .andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println(content);
	}

//	@Test
//	public void getAllLEDs() throws Exception {
//		mockMvc.perform(get(PATH)).andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
//				.andExpect(jsonPath("$", hasSize(9))).andExpect(jsonPath("$[0].row").value("0"));
//	}
//
//	@Test
//	public void updateLED() throws Exception {
//		Led led = new Led(0, 0, 10, 11, 12);
//		mockMvc.perform(put(PATH).content(toJson(led)).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk())
//				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.row").value("0"))
//				.andExpect(jsonPath("$.column").value("0")).andExpect(jsonPath("$.red").value("10"))
//				.andExpect(jsonPath("$.green").value("11")).andExpect(jsonPath("$.blue").value("12"));
//
//		Led led2 = new Led(0, 0, 0, 0, 0);
//		mockMvc.perform(put(PATH).content(toJson(led2)).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isOk())
//				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.row").value("0"))
//				.andExpect(jsonPath("$.column").value("0")).andExpect(jsonPath("$.red").value("0"))
//				.andExpect(jsonPath("$.green").value("0")).andExpect(jsonPath("$.blue").value("0"));
//	}

	private String toJson(Object object) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(object);
	}

}