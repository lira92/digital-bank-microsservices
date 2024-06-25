class OnboardingController < ApplicationController
  def pegar_clientes
    qtd_salvos = AtualizarClienteService.new.call
    return render json: { message: 'Nenhum dado novo para atualizar' }, status: :no_content if qtd_salvos.nil? || qtd_salvos.zero?

    render json: { novos_clientes: qtd_salvos }, status: 200

  end

  def prospect
     prospect = [
      {
        cpf: '5665656873337',
        status: false
      },
      {
        cpf: '363663434333',
        status: false
      },
      {
        cpf: '5465838236326',
        status: false
      }
    ]
    render json: prospect, status: 200
  end

  def enviar_status
    EnviarStatusClienteService.new(resultado_status_params).call

    render json: {}, status: 200
  end

  def resultado_status_params
    params.permit(:id, :new_status)
  end

end
