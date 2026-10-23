package lv.bootcamp.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * JSON request body for creating a new animal via the REST API.
 * Status is not included; all new animals start as AVAILABLE.
 */
public record AnimalCreateRequest(

        @Schema(description = "Animal name", example = "Milo")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(description = "Animal type", example = "CAT")
        @NotNull(message = "Type is required")
        AnimalType type,

        @Schema(description = "Animal breed", example = "Siamese")
        String breed,

        @Schema(description = "Animal age. 0 means younger then 1 year", example = "2")
        @Min(value = 0, message = "Age cannot be negative")
        Integer age,

        @Schema(description = "Animal description", example = "Friendly")
        String description,

        @Schema(description = "Image filename from /images/animals/ or a full https URL. If empty, a fallback image is chosen by animal type.", example = "Pika.jpeg")
        String imageUrl
) {}