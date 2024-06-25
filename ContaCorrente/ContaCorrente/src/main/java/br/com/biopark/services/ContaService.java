package br.com.biopark.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.biopark.controllers.ContaController;
import br.com.biopark.dtos.MovimentacaoDTO;
import br.com.biopark.enums.Tipo;
import br.com.biopark.exceptions.MinhaException;
import br.com.biopark.mapper.Mapper;
import br.com.biopark.models.Conta;
import br.com.biopark.models.Movimentacao;
import br.com.biopark.repositories.ContaRepository;
import br.com.biopark.repositories.MovimentacaoRepository;
import br.com.biopark.vos.ContaVO;

@Service
public class ContaService {

	@Autowired
	ContaRepository repository;
	@Autowired
	MovimentacaoRepository movimentacaoRepository;
	@Autowired
	NotificationService notificationService;
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ContaVO> findAll(Integer page){
		int number = page * 10;
		List<ContaVO> contas = Mapper.parseListObjects(repository.findAllPaged(number), ContaVO.class);
		contas.stream().forEach(conta -> conta.add(linkTo(methodOn(ContaController.class).findById(conta.getKey())).withSelfRel()));
		return contas;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ContaVO findById(Long id) {
		if (id == null) throw new MinhaException("Id deve ser preenchido!");
		if (repository.existsById(id) == false) throw new MinhaException("Conta não encontrada!");
		ContaVO conta = Mapper.parseObject(repository.findById(id), ContaVO.class);
		conta.add(linkTo(methodOn(ContaController.class).findById(id)).withSelfRel());
		return conta;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ContaVO findByNumero(Long numero) {
		if (numero == null) throw new MinhaException("Número deve ser preenchido!");
		if (repository.existsByNumero(numero) == 0) throw new MinhaException("Conta não encontrada!");
		ContaVO conta = Mapper.parseObject(repository.findByNumero(numero), ContaVO.class);
		conta.add(linkTo(methodOn(ContaController.class).findById(conta.getKey())).withSelfRel());
		return conta;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public double findSaldoByNumero(Long numero) {
		if (numero == null) throw new MinhaException("Número deve ser preenchido!");
		if (repository.existsByNumero(numero) == 0) throw new MinhaException("Conta não encontrada!");
		Conta conta = repository.findByNumero(numero);
		return conta.getSaldo();
	}
	
	@Transactional
	public ContaVO save(ContaVO conta) {
		if (conta.getKey() != null) throw new MinhaException("Id não deve ser preenchido!");
		if (conta.getNome() == null) throw new MinhaException("Nome deve ser preenchido!");
		if (conta.getCpf() == null) throw new MinhaException("CPF deve ser preenchido!");
		if (conta.getEmail() == null) throw new MinhaException("Email deve ser preenchido!");
		if (conta.getSaldo() < 0) throw new MinhaException("Saldo não deve ser igual ou menor que 0!");
		if (conta.getData_de_nascimento() == null) throw new MinhaException("Data de nascimento deve ser preenchido!");
		if (conta.getSenha() == null) throw new MinhaException("Senha deve ser preenchida!");
		if (conta.getTelefone() == null) throw new MinhaException("Telefone deve ser preenchido!");
		if (repository.existsByCpf(conta.getCpf()) > 0) throw new MinhaException("CPF já está sendo utilizado por uma conta!");
		if (repository.existsByEmail(conta.getEmail()) > 0) throw new MinhaException("Email já está sendo utilizado por uma conta!");
		if (repository.existsByTelefone(conta.getTelefone()) > 0) throw new MinhaException("Telefone já está sendo utilizado por uma conta!");
		Random random = new Random();
		boolean flag = false;
		Long randomLong = null;
		while (flag == false){
			randomLong = (long) random.nextInt((99999 - 10000) + 1) + 10000;
			if (repository.existsByNumero(randomLong) == 0) flag = true; 
		}
		conta.setNumero(randomLong);
		conta.setStatus(true);
		conta.setSenha(encriptPassword(conta.getSenha()));
		Conta entity = Mapper.parseObject(conta, Conta.class);
		ContaVO contaVO =  Mapper.parseObject(repository.save(entity), ContaVO.class);
		notificationService.sendWelcomeNotification(contaVO.getEmail(), contaVO.getNome(), contaVO.getNumero(), contaVO.getSaldo());
		contaVO.add(linkTo(methodOn(ContaController.class).findById(contaVO.getKey())).withSelfRel());
		return contaVO;
	}
	
	@Transactional
	public ContaVO enableAndDesable(Long numero) {
		if (numero == null) throw new MinhaException("Número deve ser preenchido!");
		if (repository.existsByNumero(numero) == 0) throw new MinhaException("Conta não encontrada!");
		Conta conta = repository.findByNumero(numero);
		if (conta.isStatus()) {
			conta.setStatus(false);
		} else {
			conta.setStatus(true);
		}
		ContaVO contaVO = Mapper.parseObject(repository.save(conta), ContaVO.class);
		contaVO.add(linkTo(methodOn(ContaController.class).findById(contaVO.getKey())).withSelfRel());
		return contaVO;
	}
	
	@Transactional
	public ContaVO debitar(MovimentacaoDTO movimentacao) {
		if (movimentacao.getNumero() == null) throw new MinhaException("Número deve ser preenchido!");
		if (movimentacao.getValor() <= 0) throw new MinhaException("Valor não pode ser igual ou menor que 0!");
		if (repository.existsByNumero(movimentacao.getNumero()) == 0) throw new MinhaException("Conta não encontrada!");
		Conta conta = repository.findByNumero(movimentacao.getNumero());
		if (conta.isStatus() == false) throw new MinhaException("Não é possível debitar pois conta está inativa!");
		if (movimentacao.getValor() > conta.getSaldo()) throw new MinhaException("Valor excede o saldo da conta!");
		conta.setSaldo(conta.getSaldo() - movimentacao.getValor());
		Movimentacao trans = new Movimentacao(movimentacao.getNome(), movimentacao.getValor(), Tipo.DEBITO, LocalDateTime.now(), conta);
		movimentacaoRepository.save(trans);
		ContaVO contaVO = Mapper.parseObject(repository.save(conta), ContaVO.class);
		contaVO.add(linkTo(methodOn(ContaController.class).findById(contaVO.getKey())).withSelfRel());
		return contaVO;
	}
	
	@Transactional
	public ContaVO creditar(MovimentacaoDTO movimentacao) {
		if (movimentacao.getNumero() == null) throw new MinhaException("Número deve ser preenchido!");
		if (movimentacao.getValor() <= 0) throw new MinhaException("Valor não pode ser igual ou menor que 0!");
		if (repository.existsByNumero(movimentacao.getNumero()) == 0) throw new MinhaException("Conta não encontrada!");
		Conta conta = repository.findByNumero(movimentacao.getNumero());
		if (conta.isStatus() == false) throw new MinhaException("Não é possível creditar pois conta está inativa!");
		conta.setSaldo(conta.getSaldo() + movimentacao.getValor());
		Movimentacao trans = new Movimentacao(movimentacao.getNome(), movimentacao.getValor(), Tipo.CREDITO, LocalDateTime.now(), conta);
		movimentacaoRepository.save(trans);
		ContaVO contaVO = Mapper.parseObject(repository.save(conta), ContaVO.class);
		contaVO.add(linkTo(methodOn(ContaController.class).findById(contaVO.getKey())).withSelfRel());
		return contaVO;
	}
	
	public String encriptPassword(String password) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		encoders.put("pbkdf2", pbkdf2Encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
		
		String result = passwordEncoder.encode(password);
		return result;
	}
}
