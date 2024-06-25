import ProspectModel, {
  Prospect,
  ProspectWithoutId,
} from "@/models/prospect/ProspectModel";

/** Serviço de prospectos */
export default class prospectService {
  /**
   * Retorna todos os prospectos
   */
  static async getAll() {
    return ProspectModel.getAll();
  }

  /**
   * Retorna um prospecto pelo id
   */
  static getById(id_prospecto: number) {
    return ProspectModel.getById(id_prospecto);
  }

  /**
   * Retorna um prospecto pelo email
   */
  static getByEmail(email: string) {
    return ProspectModel.getByEmail(email);
  }

  /**
   * Cria um prospecto
   */
  static create(prospecto: ProspectWithoutId) {
    this.validate(prospecto);
    return ProspectModel.create(prospecto);
  }

  /**
   * Atualiza um prospecto
   */
  static update(id_prospecto: number, prospecto: Prospect) {
    return ProspectModel.update(id_prospecto, prospecto);
  }

  /**
   * Deleta um prospecto
   */
  static delete(id_prospecto: number) {
    return ProspectModel.delete(id_prospecto);
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
