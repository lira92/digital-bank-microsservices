import ProspectModel, {
  Prospect,
  ProspectWithoutId,
} from "@/models/prospect/ProspectModel";
import AccountService from "../account/AccountService";

/** Serviço de prospectos */
export default class prospectService {
  /**
   * Retorna todos os prospectos
   */
  static async getAll() {
    return await ProspectModel.getAll();
  }

  /**
   * Retorna um prospecto pelo id
   */
  static async getById(id_prospecto: number) {
    return await ProspectModel.getById(id_prospecto);
  }

  /**
   * Retorna um prospecto pelo email
   */
  static async getByEmail(email: string) {
    return await ProspectModel.getByEmail(email);
  }

  /**
   * Cria um prospecto
   */
  static async create(prospecto: ProspectWithoutId) {
    this.validate(prospecto);
    return await ProspectModel.create(prospecto);
  }

  /**
   * Atualiza um prospecto
   */
  static async update(id_prospecto: number, prospecto: Prospect) {
    if (prospecto.status === true) {
      const prospect = await ProspectModel.getById(id_prospecto)
      if(!prospect) throw new Error('Prospecto não encontrado');
      const account = await AccountService.create({
        nome: prospect.nome,
        cpf: prospect.documento,
        email: prospect.email,
        data_de_nascimento: prospect.data_nascimento.toString(),
        telefone: prospect.telefone,
        senha: prospect.senha,
      });
      if (account.success) {
        return await ProspectModel.update(id_prospecto, prospecto);
      } else {
        throw new Error(
          `Não foi possível criar a conta do prospecto ${account.message}`
        );
      }
    } else {
      return await ProspectModel.update(id_prospecto, {
        ...prospecto,
        status: false,
      });
    }
  }

  /**
   * Deleta um prospecto
   */
  static async delete(id_prospecto: number) {
    return await ProspectModel.delete(id_prospecto);
  }

  static validate(prospecto: ProspectWithoutId) {
    if (!prospecto.nome || prospecto.nome.length < 3) {
      throw new Error("Nome é obrigatório");
    }
    if (!prospecto.email || prospecto.email.length < 3) {
      throw new Error("Email é obrigatório");
    }
    if (!prospecto.documento || prospecto.documento.length < 3) {
      throw new Error("Documento é obrigatório");
    }
    if (!prospecto.senha || prospecto.senha.length < 3) {
      throw new Error("Senha é obrigatória");
    }
  }
}
