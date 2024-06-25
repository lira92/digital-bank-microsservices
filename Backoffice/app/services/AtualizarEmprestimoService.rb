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

    emprestimos = JSON.parse @response.body
    quantidade_emprestimos_salvos = 0

    emprestimos.each do |emprestimo_data|
      emprestimo = Emprestimo.new(
        emprestimo_id: emprestimo_data['emprestimo'],
        conta: emprestimo_data['conta'],
        valor: emprestimo_data['valor'],
        status: emprestimo_data['status']
      )
      quantidade_emprestimos_salvos += 1 if emprestimo.save
    end

    quantidade_emprestimos_salvos
  end
end
