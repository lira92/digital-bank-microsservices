class CreateConfiguracaoEmprestimos < ActiveRecord::Migration[7.1]
  def change
    create_table :configuracao_emprestimos do |t|
      t.float :juros
      t.integer :minimo_parcelamento
      t.integer :maximo_parcelamento

      t.timestamps
    end
  end
end
