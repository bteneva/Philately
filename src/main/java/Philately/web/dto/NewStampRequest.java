package Philately.web.dto;

import Philately.stamp.model.Paper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class NewStampRequest {

    @NotBlank
    @Size(min = 5, max = 20, message = "Name length must be between 5 and 20 characters!")
    private String name;

    @NotBlank
    @Size(min = 5, max = 25, message = "Description length must be between 5 and 25 characters!")
    private String description;

    @NotBlank(message = "Please enter a valid image URL!")
    @URL(message = "Please enter a valid image URL!")
    private String imageURL;

    @NotNull(message = "You must select a type of paper!")
    private Paper paper;


}
