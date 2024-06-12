package br.com.biopark.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.biopark.dtos.FiltragemDTO;
import br.com.biopark.enums.Tipo;
import br.com.biopark.services.MovimentacaoService;
import br.com.biopark.util.MediaType;
import br.com.biopark.vos.MovimentacaoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/movimentacao")
@Tag(name = "Movimentações", description = "Endpoints para gerenciamento das movimentações")
public class MovimentacaoController {

	@Autowired
	MovimentacaoService service;
	
	@GetMapping(value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Encontrar uma movimentação", description = "Encontrar uma movimentação pelo seu ID", tags = {"Movimentações"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MovimentacaoVO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = MovimentacaoVO.class)),
					@Content(mediaType = "application/x-yaml", schema = @Schema(implementation = MovimentacaoVO.class))
				}
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public MovimentacaoVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Busca e filtra uma lista de movimentações", description = "Busca e filtra uma lista de movimentações", tags = {"Movimentações"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MovimentacaoVO.class))),
					@Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = MovimentacaoVO.class))),
					@Content(mediaType = "application/x-yaml", array = @ArraySchema(schema = @Schema(implementation = MovimentacaoVO.class))),
				}
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public List<MovimentacaoVO> filtragem(@RequestParam(value = "numero", required = false) Long numero,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "tipo", required = false) Tipo tipo,
			@RequestParam(value = "data_igual", required = false) String data_igual,
			@RequestParam(value = "data_maior_que", required = false) String data_maior_que,
			@RequestParam(value = "data_menor_que", required = false) String data_menor_que,
			@RequestParam(value = "page", defaultValue = "0") Integer page){
		FiltragemDTO filtros = new FiltragemDTO(numero, nome, tipo, data_maior_que, data_menor_que, data_igual);
		return service.filtragem(filtros, page);
	}
}
