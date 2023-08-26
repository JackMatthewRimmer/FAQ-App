package com.faq.Services;

import com.faq.common.Exceptions.ApiException;
import com.faq.common.Requests.QuestionRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QuestionService {

    public Map<String, String> createQuestion(@NonNull QuestionRequest questionRequest,
                                              @NonNull UserDetails principal) throws ApiException {
        questionRequest.validate();
        Map<String, String> response = new HashMap<>();
        response.put("Location", "Resource");
        return response;

    }
}
