package com.cano7.demo.domain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cano7.demo.domain.model.Recipe;
import com.cano7.demo.domain.service.RecipeService;
import com.cano7.demo.dto.RecipeDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Recipes", description = "Gestión de recetas de yogurt")
@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    
    private final RecipeService recipeService;
    
    @Operation(summary = "Crear receta", description = "Registra una nueva receta de yogurt con ingredientes y parámetros de proceso.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Receta creada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
        @ApiResponse(responseCode = "400", description = "Datos de receta inválidos")
    })
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la receta", required = true, content = @Content(schema = @Schema(implementation = RecipeDTO.class))) @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.createRecipe(recipeDTO);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Actualizar receta", description = "Modifica una receta existente por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Receta actualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@Parameter(description = "ID de la receta", required = true) @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados de la receta", required = true, content = @Content(schema = @Schema(implementation = RecipeDTO.class))) @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.updateRecipe(id, recipeDTO);
        return ResponseEntity.ok(recipe);
    }
    
    @Operation(summary = "Obtener receta por ID", description = "Devuelve la receta solicitada por su identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Receta encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@Parameter(description = "ID de la receta", required = true) @PathVariable Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        return ResponseEntity.ok(recipe);
    }
    
    @Operation(summary = "Listar recetas activas", description = "Devuelve todas las recetas activas disponibles.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de recetas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class)))
    })
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllActiveRecipes());
    }
    
    @Operation(summary = "Buscar recetas", description = "Busca recetas que coincidan con una palabra clave.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recetas encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@Parameter(description = "Palabra clave para buscar recetas", required = true) @RequestParam String keyword) {
        return ResponseEntity.ok(recipeService.searchRecipes(keyword));
    }
    
    @Operation(summary = "Desactivar receta", description = "Desactiva una receta para que no se use en nuevos lotes.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Receta desactivada"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateRecipe(@Parameter(description = "ID de la receta", required = true) @PathVariable Long id) {
        recipeService.deactivateRecipe(id);
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "Activar receta", description = "Reactiva una receta previamente desactivada.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Receta activada"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateRecipe(@Parameter(description = "ID de la receta", required = true) @PathVariable Long id) {
        recipeService.activateRecipe(id);
        return ResponseEntity.ok().build();
    }
}
