package lv.bootcamp.shelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lv.bootcamp.shelter.dto.AnimalCreateRequest;
import lv.bootcamp.shelter.dto.AnimalResponse;
import lv.bootcamp.shelter.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for shelter animal endpoints.
 * Returns JSON — does not render HTML pages.
 */
@Tag(name = "Animals", description = "View, create and adopt shelter animals")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/animals")
public class AnimalApiController {

    private final AnimalService animalService;

    @Operation(summary = "List all animals",
            description = "Returns every animal in the shelter, both available and adopted, sorted by ID.")
    @ApiResponse(responseCode = "200", description = "List of animals returned")
    @GetMapping
    public List<AnimalResponse> findAll() {
        return animalService.findAll();
    }

    @Operation(summary = "Get animal by id")
    @ApiResponse(responseCode = "200", description = "Animal found")
    @ApiResponse(responseCode = "400", description = "No animal with this id",
            content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> findById(
            @Parameter(description = "ID of the animal", example = "1") @PathVariable Long id) {
        return animalService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lists adopted animals.
     */
    @Operation(summary = "List adopted animals",
            description = "Returns only animals with status ADOPTED, sorted by ID.")
    @ApiResponse(responseCode = "200",
            description = "List of adopted animals returned (can be empty)")
    @GetMapping("/adopted")
    public List<AnimalResponse> findAdopted() {
        return animalService.findAdopted();
    }

    /**
     * Creates a new animal.
     */

    @Operation(summary = "Create a new animal",
            description = "Creates an animal with status AVAILABLE, if no image is given, a fallback image is chosen by animal type.")
    @ApiResponse(responseCode = "201",
            description = "New animal created")
    @ApiResponse(responseCode = "400",
            description = "Invalid request: name is blank, type is missing or age is negative",
            content = @Content)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponse create(@RequestBody @Valid AnimalCreateRequest request) {
        return animalService.create(request);
    }

    /**
     * Adopts an animal
     */
    @Operation(summary = "Adopt an animal",
            description = "Changes the animal status to ADOPTED and records today's date as the adoption date.")
    @ApiResponse(responseCode = "200", description = "Animal adopted")
    @ApiResponse(responseCode = "404", description = "No animal with this ID",
            content = @Content)
    @ApiResponse(responseCode = "409", description = "Animal is already adopted",
            content = @Content)
    @PostMapping("/{id}/adopt")
    public ResponseEntity<AnimalResponse> adopt(
            @Parameter(description = "ID of the animal to adopt", example = "1") @PathVariable Long id) {
        return animalService.adopt(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleAlreadyAdopted(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
