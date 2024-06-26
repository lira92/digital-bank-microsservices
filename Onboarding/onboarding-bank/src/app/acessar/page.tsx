import Application from "@/components/application/Application";
import LoginView from "@/views/login/LoginView";

import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Biobank - Banco digital do Biopark: Acesse sua conta",
  description: "Acesse sua conta no Biobank!",
};

export default function Login() {
  return (
    <Application>
      <LoginView />
    </Application>
  );
}
