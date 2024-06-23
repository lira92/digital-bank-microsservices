# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `bin/rails
# db:schema:load`. When creating a new database, `bin/rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema[7.1].define(version: 2024_06_23_212152) do
  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "configuracao_emprestimos", force: :cascade do |t|
    t.float "juros"
    t.integer "minimo_parcelamento"
    t.integer "maximo_parcelamento"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "configuracao_investimentos", force: :cascade do |t|
    t.float "juros"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "emprestimos", force: :cascade do |t|
    t.integer "emprestimo_id"
    t.string "conta"
    t.string "valor"
    t.integer "status", default: 0, null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

end