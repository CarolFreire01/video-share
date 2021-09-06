package br.com.carol.videoshare.expections;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }

}
