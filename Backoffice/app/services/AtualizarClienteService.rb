class AtualizarClienteService
  def initialize
  end

  def call
    enviar_requisicao
    salvar_clientes
  end

  private

  def enviar_requisicao
    uri = URI("#{ENV['api_onboarding']}/api/prospect")
    @response = Faraday.get uri
  end

  def salvar_clientes
    return nil unless @response.success?

    clientes = JSON.parse @response.body
    quantidade_clientes_salvos = 0

    clientes.each do |clientes_data|
      cliente = Onboarding.new(
        cpf: clientes_data['cpf']
      )
      quantidade_clientes_salvos += 1 if cliente.save
    end
    quantidade_clientes_salvos
  end
end