package com.gaebaljip.exceed.common.util;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public interface ApiDocumentUtil {
    static OperationRequestPreprocessor getDocumentRequest() {
        // Request Spec을 정렬해서 출력해줌
        return preprocessRequest(prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        // Response Spec을 정렬해서 출력해줌
        return preprocessResponse(prettyPrint());
    }
}
