class ChangeEmprestimoIdToStringInEmprestimos < ActiveRecord::Migration[7.1]
  def up
    change_column :emprestimos, :emprestimo_id, :string
  end

  def down
    change_column :emprestimos, :emprestimo_id, :integer
  end
end
