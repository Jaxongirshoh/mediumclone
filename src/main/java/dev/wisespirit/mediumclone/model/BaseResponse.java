package dev.wisespirit.mediumclone.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseResponse<T>(T result, ErrorData errorData,boolean success) {

    public BaseResponse(T result){
        this(result,null,true);
    }

    public BaseResponse(ErrorData errorData){
        this(null,errorData,false);
    }
}
