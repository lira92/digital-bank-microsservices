class RabbitmqConsumer
  def initialize(queue_name)
    @queue_name = queue_name
    @channel = Rails.application.config.bunny_connection.create_channel
    @queue = @channel.queue(queue_name)
  end

  def start
    @queue.subscribe(block: true) do |_delivery_info, _properties, body|
      process_message(body)
    end
  end

  private

  def process_message(message)
    puts "Received message: #{message}"
    data = JSON.parse(message)
  rescue JSON::ParserError => e
    puts "Failed to parse message: #{e.message}"
  end
end
