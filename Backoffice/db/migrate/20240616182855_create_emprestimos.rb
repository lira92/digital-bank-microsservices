class CreateEmprestimos < ActiveRecord::Migration[7.1]
  def change
    create_table :emprestimos do |t|
      t.integer :emprestimo_id
      t.bigint :conta
      t.float :valor
      t.integer :status, default: 0, null: false

      t.timestamps
    end
  end
end
