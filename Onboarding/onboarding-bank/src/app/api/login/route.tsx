import AccountService from "@/services/account/AccountService";

export async function POST(request: Request) {
  const body: {
    email: string;
    senha: string;
  } = await request.json();
  try {
    return new Response(
      JSON.stringify(await AccountService.login(body.email, body.senha)),
      {
        status: 200,
        headers: {
          "content-type": "application/json",
        },
      }
    );
  } catch (error: any) {
    return new Response(
      JSON.stringify({
        message: error?.message || "Ocorreu um erro",
        success: false,
      }),
      {
        status: 400,
        headers: {
          "content-type": "application/json",
        },
      }
    );
  }
}
