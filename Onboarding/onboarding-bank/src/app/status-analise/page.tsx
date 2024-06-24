import Application from "@/components/application/Application";
import StatusView from "@/views/status/StatusView";

import type { Metadata } from 'next'
 
export const metadata: Metadata = {
  title: 'Biobank - Banco digital do Biopark: Status da análise',
  description: 'Verifique o status da sua conta no Biobank!',
}

export default function Home() {
  return (
    <Application>
      <StatusView />
    </Application>
  );
}
