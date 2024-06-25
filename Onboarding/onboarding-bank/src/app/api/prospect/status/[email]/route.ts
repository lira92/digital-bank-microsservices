import prospectService from "@/services/prospect/ProspectService";

export async function GET(
  r: Request,
  { params }: { params: { email: string } }
) {
  try {
    const emailProspect = String(params.email);
    return new Response(
      JSON.stringify(await prospectService.getByEmail(emailProspect)),
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
