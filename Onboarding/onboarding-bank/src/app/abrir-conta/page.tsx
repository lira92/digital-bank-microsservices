import Application from "@/components/application/Application";
import RegisterView from "@/views/register/RegisterView";

import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Biobank - Banco digital do Biopark: Abra sua conta!",
  description: "Abra sua conta no Biobank!",
};

export default function Home() {
  return (
    <Application>
      <RegisterView />
    </Application>
  );
}
