class EmprestimosController < ApplicationController
  rescue_from 'ApiError::Base' do |exception|
    render json: exception, status: 500
  end

  def pegar_emprestimos
    emprestimos = AtualizarEmprestimoService.new.call
    return render json: { message: 'Nenhum dado novo para atualizar' }, status: :no_content if emprestimos.nil?

    render json: { emprestimos_pendentes: emprestimos }, status: 200
  end

  def enviar_resultado_validacao
    requisicao = EnviarAprovacaoOuReprovacaoService.new(resultado_validacao_params).call
    render json: {}, status: requisicao.status
  end

  def teste_validar_emprestimo
    render json: {}, status: 200
  end

  def teste_emprestimos
    emprestimos = [
      {
        emprestimo_id: 15,
        conta: 1,
        valor: 15,
        status: 'pendente'
      },
      {
        emprestimo: 12,
        conta: 65,
        valor: 15,
        status: 'pendente'
      },
      {
        emprestimo: 13,
        conta: 41,
        valor: 55,
        status: 'pendente'
      }
    ]
    render json: emprestimos, status: 200
  end

  private

  def resultado_validacao_params
    params.permit(:id, :new_status)
  end
end
