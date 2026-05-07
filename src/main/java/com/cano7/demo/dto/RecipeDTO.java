package com.cano7.demo.dto;

import java.util.List;

import com.cano7.demo.domain.model.Recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO para crear y actualizar recetas de yogurt")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    @Schema(description = "Nombre de la receta", example = "Yogurt natural")
    private String name;
    @Schema(description = "Descripción de la receta", example = "Yogurt cremoso con sabor natural")
    private String description;
    @Schema(description = "Volumen de leche por defecto en litros", example = "2.0")
    private Double defaultMilkVolume;
    @Schema(description = "Cantidad de cultivo por defecto en gramos", example = "0.05")
    private Double defaultStarterAmount;
    @Schema(description = "Temperatura de calentamiento esperada en grados Celsius", example = "85.0")
    private Double heatingTemperature;
    @Schema(description = "Duración del calentamiento en minutos", example = "15")
    private Integer heatingDuration;
    @Schema(description = "Temperatura de inoculación en grados Celsius", example = "43.0")
    private Double inoculationTemperature;
    @Schema(description = "Temperatura de incubación en grados Celsius", example = "42.0")
    private Double incubationTemperature;
    @Schema(description = "Tiempo mínimo de incubación en minutos", example = "240")
    private Integer minIncubationTime;
    @Schema(description = "Tiempo máximo de incubación en minutos", example = "300")
    private Integer maxIncubationTime;
    @Schema(description = "Tiempo de refrigeración en minutos", example = "60")
    private Integer refrigerationTime;
    @Schema(description = "Nivel de dificultad de la receta")
    private Recipe.DifficultyLevel difficulty;
    @Schema(description = "Consejos adicionales para la preparación", example = "Usa leche entera para mejor textura")
    private String tips;
    @Schema(description = "Lista de ingredientes necesarios para la receta")
    private List<IngredientDTO> ingredients;
}
