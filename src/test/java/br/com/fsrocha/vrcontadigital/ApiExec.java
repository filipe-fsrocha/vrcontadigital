package br.com.fsrocha.vrcontadigital;

import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@UtilityClass
public class ApiExec {
    public static MvcResult doPost(MockMvc mvc, String uri, String json) throws Exception {
        var post = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        return mvc.perform(post).andReturn();
    }

    public static MvcResult doGet(MockMvc mvc, String uri) throws Exception {
        var get = MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        return mvc.perform(get).andReturn();
    }
}
