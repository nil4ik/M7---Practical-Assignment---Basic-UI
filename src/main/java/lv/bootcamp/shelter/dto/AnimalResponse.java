package lv.bootcamp.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lv.bootcamp.shelter.model.AnimalStatus;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * Response body for a single animal returned by the API.
 */
public record AnimalResponse(
        @Schema(description = "Animal ID", example = "1")
        Long id,

        @Schema(description = "Animal name", example = "Luna")
        String name,

        @Schema(description = "Animal type", example = "CAT")
        AnimalType type,

        @Schema(description = "Animal breed", example = "Bengal")
        String breed,

        @Schema(description = "Animal age", example = "2")
        Integer age,

        @Schema(description = "Animal description", example = "Friendly.")
        String description,

        @Schema(description = "Animal adoption status", example = "AVAILABLE")
        AnimalStatus status,

        @Schema(description = "Path or URL of the animal's image", example = "/images/animals/Luna.jpeg")
        String imageUrl,

        @Schema(description = "Adoption date note. Null if the animal is not adopted.",
                example = "adopted on 2026-06-01")
        String adoptionNote
) {}
