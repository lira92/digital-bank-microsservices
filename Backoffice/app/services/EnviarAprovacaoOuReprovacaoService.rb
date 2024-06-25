class EnviarAprovacaoOuReprovacaoService
  def initialize(params)
    @id = params['id']
    @new_status = params['new_status'].to_s.downcase == 'aprovado'
  end

  def call
    pegar_emprestimo
    enviar_requisicao
  end

  private

  def pegar_emprestimo
    @emprestimo = Emprestimo.find @id
    return nil unless @emprestimo

    @emprestimo
  end

  def enviar_requisicao
    uri = URI("#{ENV['api_emprestimo']}/validar_emprestimo")
    params = { emprestimo: @emprestimo.id, aprovado: @new_status }
    headers = { 'Content-Type': 'application/json' }
    @response = Faraday.post uri, params.to_json, headers
  end
end
