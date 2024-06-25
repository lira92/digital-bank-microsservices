class ConfiguracaoEmprestimo < ApplicationRecord
  validate :minimo_nao_maior_que_maximo

  private

  def minimo_nao_maior_que_maximo
    return if minimo_parcelamento.blank? || maximo_parcelamento.blank?

    return if minimo_parcelamento < maximo_parcelamento

    errors.add(:minimo_parcelamento, 'O parcelamento minimo não pode ser maior que o máximo')
  end
end
