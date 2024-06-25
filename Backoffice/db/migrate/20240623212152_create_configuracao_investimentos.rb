class CreateConfiguracaoInvestimentos < ActiveRecord::Migration[7.1]
  def change
    create_table :configuracao_investimentos do |t|
      t.float :juros

      t.timestamps
    end
  end
end
