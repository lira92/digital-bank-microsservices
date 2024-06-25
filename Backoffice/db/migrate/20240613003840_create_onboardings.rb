class CreateOnboardings < ActiveRecord::Migration[7.1]
  def change
    create_table :onboardings do |t|
      t.string :cpf
      t.boolean :status, default: false

      t.timestamps
    end
  end
end
