class Emprestimo < ApplicationRecord
  validates :emprestimo_id, :conta, :valor, :status, presence: true
  validates :emprestimo_id, uniqueness: true
  
  enum status: { pendente: 0, aprovado: 1, reprovado: 2 }
end
