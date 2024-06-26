package br.com.biopark.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.biopark.dtos.LoginDTO;
import br.com.biopark.dtos.MovimentacaoDTO;
import br.com.biopark.services.ContaService;
import br.com.biopark.util.MediaType;
import br.com.biopark.vos.ContaVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/conta")
@Tag(name = "Contas Corrente", description = "Endpoints para gerenciamento das contas corrente")
public class ContaController {

	@Autowired
	ContaService service;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Encontrar todas as contas corrente", description = "Encontrar todas as contas corrente", tags = {"Contas Corrente"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {
						@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ContaVO.class))),
						@Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = ContaVO.class))),
						@Content(mediaType = "application/x-yaml", array = @ArraySchema(schema = @Schema(implementation = ContaVO.class))),
					}
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public List<ContaVO> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page){
		return service.findAll(page);
	}
	
	@GetMapping(value = "/{id}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Encontrar uma conta corrente", description = "Encontrar uma conta corrente pelo seu ID", tags = {"Contas Corrente"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ContaVO.class))
				}
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ContaVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@GetMapping(value = "/numero/{numero}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Encontrar uma conta corrente", description = "Encontrar uma conta corrente pelo seu número", tags = {"Contas Corrente"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ContaVO.class))
				}
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ContaVO findByNumero(@PathVariable(value = "numero") Long numero) {
		return service.findByNumero(numero);
	}
	
	@PostMapping(value = "/login",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Executar o login de uma conta", description = "Executar o login de uma conta com seu email e senha", tags = {"Contas Corrente"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ContaVO.class))
				}
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ContaVO findByEmail(@RequestBody LoginDTO login) {
		return service.findByEmail(login.getEmail(), login.getSenha());
	}
	
	@GetMapping(value = "/saldo/{numero}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Encontrar o saldo de uma conta corrente", description = "Encontrar o saldo de uma conta corrente pelo seu número", tags = {"Contas Corrente"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Double.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = Double.class)),
					@Content(mediaType = "application/x-yaml", schema = @Schema(implementation = Double.class))
				}
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public double findSaldoByNumero(@PathVariable(value = "numero") Long numero) {
		return service.findSaldoByNumero(numero);
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Cadastrar uma conta corrente", description = "Cadastrar uma conta corrente por JSON, XML ou YAML", tags = {"Contas Corrente"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ContaVO.class))
				}
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ContaVO save(@RequestBody ContaVO conta) {
		return service.save(conta);
	}
	
	@PatchMapping(value = "/{numero}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Ativar/inativar uma conta corrente", description = "Ativar/inativar uma conta corrente pelo seu número", tags = {"Contas Corrente"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ContaVO.class))
				}
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ContaVO enableAndDesable(@PathVariable(value = "numero") Long numero) {
		return service.enableAndDesable(numero);
	}
	
	@PatchMapping(value = "/debitar",
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Debitar de uma conta corrente", description = "Debitar (retirar dinheiro) de uma conta corrente pelo seu número, nome (da transação) (opcional) e valor", tags = {"Contas Corrente"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ContaVO.class))
				}
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ContaVO debitar(@RequestBody MovimentacaoDTO movimentacao) {
		return service.debitar(movimentacao);
	}
	
	@PatchMapping(value = "/creditar",
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Creditar de uma conta corrente", description = "Creditar (adicionar dinheiro) de uma conta corrente pelo seu número, nome (da transação) (opcional) e valor", tags = {"Contas Corrente"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = ContaVO.class)),
					@Content(mediaType = "application/x-yaml", schema = @Schema(implementation = ContaVO.class))
				}
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ContaVO creditar(@RequestBody MovimentacaoDTO movimentacao) {
		return service.creditar(movimentacao);
	}
}
