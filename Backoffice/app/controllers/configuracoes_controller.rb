class ConfiguracoesController < ApplicationController
  def emprestimo
    @configuracao = ConfiguracaoEmprestimo.first
    return render json: { message: 'Nenhuma configuração encontrada' }, status: 404 if @configuracao.nil?

    render json: @configuracao, status: :ok
  end

  def criar_atualizar_emprestimo
    @configuracao = ConfiguracaoEmprestimo.first_or_initialize

    if @configuracao.update(emprestimo_params)
      render json: @configuracao, status: @configuracao.new_record? ? :created : :ok
    else
      render json: @configuracao.errors, status: :unprocessable_entity
    end
  end

  def investimento
    @configuracao = ConfiguracaoInvestimento.first
    return render json: { message: 'Nenhuma configuração encontrada' }, status: 404 if @configuracao.nil?

    render json: @configuracao, status: :ok
  end

  def criar_atualizar_investimento
    @configuracao = ConfiguracaoInvestimento.first_or_initialize

    if @configuracao.update(investimento_params)
      render json: @configuracao, status: @configuracao.new_record? ? :created : :ok
    else
      render json: @configuracao.errors, status: :unprocessable_entity
    end
  end

  private

  def emprestimo_params
    params.permit(:juros, :minimo_parcelamento, :maximo_parcelamento)
  end

  def investimento_params
    params.permit(:juros)
  end
end
