export type Account = {
  nome: string;
  cpf: string;
  email: string;
  data_de_nascimento: string;
  telefone: string;
  senha: string;
  saldo?: number;
};

/**
 * Serviço de contas
 */
export default class AccountService {
  /**
   * URL de comunicação com a API de contas
   */
  static END_POINT = "http://localhost:3003";

  /**
   * Cria uma nova conta na API de conta corrente
   */
  static async create(account: Account) {
    try {
      const request = await fetch(`${AccountService.END_POINT}/api/conta`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(account),
      });
      const response = await request.json();
      if (request.ok) {
        return {
          success: true,
          message: "Conta criada com sucesso",
          data: response,
        };
      } else {
        return {
          success: false,
          message: response?.message || "Sem resposta da integração",
        };
      }
    } catch (e: any) {
      return {
        success: false,
        message: `Falha de integração ${e.message}`,
      };
    }
  }

  /**
   * Faz login na API de conta corrente e retorna os dados da conta
   */
  static async getAll() {
    try {
      const request = await fetch(
        `${AccountService.END_POINT}/api/conta?page=0`,
        {
          method: "GET",
        }
      );
      const response = await request.json();
      if (request.ok) {
        return {
          success: true,
          message: "Dados da conta retornados com sucesso",
          data: response,
        };
      } else {
        return {
          success: false,
          message: response?.message || "Sem resposta da integração",
        };
      }
    } catch (e: any) {
      return {
        success: false,
        message: `Falha de integração ${e.message}`,
      };
    }
  }

  /**
   * Faz login na API de conta corrente e retorna os dados da conta
   */
  static async login(email: string, senha: string) {
    try {
      const request = await fetch(
        `${AccountService.END_POINT}/api/conta/login`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ email, senha }),
        }
      );
      const response = await request.json();
      if (request.ok) {
        return {
          success: true,
          message: "Login realizado com sucesso",
          data: response,
        };
      } else {
        return {
          success: false,
          message: response?.message || "Sem resposta da integração",
        };
      }
    } catch (e: any) {
      return {
        success: false,
        message: `Falha de integração ${e.message}`,
      };
    }
  }
}
