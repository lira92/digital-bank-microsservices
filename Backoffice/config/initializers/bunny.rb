require 'bunny'

# Método para verificar se o RabbitMQ está pronto
def rabbitmq_ready?
  begin
    connection = Bunny.new(
      host: 'rabbitmq',
      port: 5672,
      vhost: '/',
      user: 'guest',
      pass: 'guest'
    )

    # Tenta estabelecer a conexão
    connection.start

    # Verifica se a conexão está aberta
    return connection.open?
  rescue Bunny::TCPConnectionFailed => e
    puts "A conexão com o RabbitMQ falhou. Tentando novamente em 5 segundos..."
    sleep 5
    retry
  rescue Bunny::PossibleAuthenticationFailureError => e
    puts "Falha de autenticação no RabbitMQ. Verifique as credenciais e tente novamente."
    return false
  rescue StandardError => e
    puts "Erro inesperado: #{e.message}"
    return false
  end
end

# Aguarda até que o RabbitMQ esteja pronto
while !rabbitmq_ready?
  puts "Aguardando RabbitMQ..."
  sleep 5
end

# Conexão com o RabbitMQ
connection = Bunny.new(
  host: 'rabbitmq',
  port: 5672,
  vhost: '/',
  user: 'guest',
  pass: 'guest'
)

connection.start

# Makes the connection available globally
Rails.application.config.bunny_connection = connection
