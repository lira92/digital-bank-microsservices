class AtualizarEmprestimoService
  def initialize
  end

  def call
    enviar_requisicao
    salvar_novos_emprestimos
  end

  private

  def enviar_requisicao
    uri = URI("#{ENV['api_emprestimo']}/emprestimos")
    @response = Faraday.get uri
  end

  def salvar_novos_emprestimos
    return nil unless @response.success?

    response_json = JSON.parse @response.body
    return nil if response_json['emprestimos'].size <= 0

    quantidade_emprestimos_salvos = 0

    response_json['emprestimos'].each do |emprestimo_data|
      emprestimo = Emprestimo.new(
        emprestimo_id: emprestimo_data['id'],
        conta: emprestimo_data['numero_conta'],
        valor: emprestimo_data['valor'],
        status: emprestimo_data['status']
      )
      quantidade_emprestimos_salvos += 1 if emprestimo.save
    end
    emprestimos = Emprestimo.where status: 0
 
    emprestimos
  end
end
