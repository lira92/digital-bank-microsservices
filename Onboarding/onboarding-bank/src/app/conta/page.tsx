import Application from "@/components/application/Application";
import AccountView from "@/views/account/AccountView";

import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Biobank - Banco digital do Biopark: Minha conta",
  description: "Veja detalhes da sua conta no Biobank!",
};

export default function Home() {
  return (
    <Application>
      <AccountView />
    </Application>
  );
}
