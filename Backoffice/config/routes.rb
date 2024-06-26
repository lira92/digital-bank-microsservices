Rails.application.routes.draw do
  
  get '/pegar_emprestimos', to: 'emprestimos#pegar_emprestimos', as: 'pegar_emprestimos'
  resources :emprestimos, except: %i[index new create show] do
    member do
      post 'enviar_resultado_validacao'
    end
  end

  get '/configuracao/emprestimo', to: 'configuracoes#emprestimo', as: 'configuracao_emprestimo'
  post '/configuracao/emprestimo', to: 'configuracoes#criar_atualizar_emprestimo', as: 'configuracao_atualizar_emprestimo'

  get '/configuracao/investimento', to: 'configuracoes#investimento', as: 'configuracao_investimento'
  post '/configuracao/investimento', to: 'configuracoes#criar_atualizar_investimento', as: 'configuracao_atualizar_investimento'

  get '/pegar_clientes', to: 'onboarding#pegar_clientes', as: 'pegar_clientes'
  post '/enviar_clientes', to: 'onboarding#enviar_status', as: 'enviar_status'

  # Endpoints dos outros microsserviços
  get '/emprestimos', to: 'emprestimos#teste_emprestimos', as: 'emprestimos'
  post '/validar_emprestimo', to: 'emprestimos#teste_validar_emprestimo', as: 'validar_emprestimo'
  get '/api/prospect', to: 'onboarding#teste_upstream_prospect', as: 'onboarding_prospect'
  put '/api/prospect', to: 'onboarding#teste_downstream_prospect', as: 'teste_put'

  get "up" => "rails/health#show", as: :rails_health_check
end