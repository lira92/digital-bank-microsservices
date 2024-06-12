package br.com.biopark.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.biopark.models.Conta;
import br.com.biopark.models.Movimentacao;
import br.com.biopark.vos.ContaVO;
import br.com.biopark.vos.MovimentacaoVO;

public class Mapper {

	private static ModelMapper mapper = new ModelMapper();

	static {
		mapper.createTypeMap(Conta.class, ContaVO.class).addMapping(Conta::getId, ContaVO::setKey);
		mapper.createTypeMap(ContaVO.class, Conta.class).addMapping(ContaVO::getKey, Conta::setId);
		mapper.createTypeMap(Movimentacao.class, MovimentacaoVO.class).addMapping(Movimentacao::getId, MovimentacaoVO::setKey);
		mapper.createTypeMap(MovimentacaoVO.class, Movimentacao.class).addMapping(MovimentacaoVO::getKey, Movimentacao::setId);
	}
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<D>();
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}
}
