package com.gaebaljip.exceed.common.util;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

public interface ApiDocumentUtil {
    static OperationRequestPreprocessor getDocumentRequest() {
        // Request Spec을 정렬해서 출력해줌
        return preprocessRequest(
                modifyUris().scheme("http").host("43.203.82.233").port(8080), prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        // Response Spec을 정렬해서 출력해줌
        return preprocessResponse(prettyPrint());
    }
}
