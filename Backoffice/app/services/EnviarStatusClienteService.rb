class EnviarStatusClienteService
    def initialize(params)
        @id = params["id"]
        @new_status = params["new_status"]
    end

    def call
        pegar_cliente
        enviar_status
    end

    def pegar_cliente
        @onboarding = Onboarding.find @id
        return nil unless @emprestimo

        @onboarding
    end
    
    def enviar_status
        uri = URI("#{ENV['api_onboarding']}/api/downstream/prospect") 
        body = { cpf: @onboarding.cpf, status: @new_status }
        headers = { 'Content-Type': 'applcation/json' }
        @response = Faraday.put uri, body.to_json, headers
    end

end


