package br.com.carol.videoshare.expections;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ObjectNotFoundExpection extends RuntimeException{

    private static final long serialVersionUID = 1L;


    public ObjectNotFoundExpection(String message) {
        super(message);
    }


}
