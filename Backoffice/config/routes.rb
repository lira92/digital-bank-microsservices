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

  # Endpoints de Teste
  get '/emprestimos', to: 'emprestimos#emprestimos', as: 'emprestimos'
  post '/validar_emprestimo', to: 'emprestimos#validar_emprestimo', as: 'validar_emprestimo'
  get '/api/upstream/prospect', to: 'onboarding#prospect', as: 'onboarding_prospect'
  put '/api/downstream/prospect', to: 'onboarding#pegar_clientes', as: 'pegar_clientes'

  get "up" => "rails/health#show", as: :rails_health_check
end